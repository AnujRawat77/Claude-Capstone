$ErrorActionPreference = 'Stop'
$repositoryRoot = Resolve-Path (Join-Path $PSScriptRoot '..')
Set-Location $repositoryRoot

function Assert-NoSourceMatches {
	param(
		[string]$Pattern,
		[string[]]$Paths,
		[string]$Check
	)

	$matches = foreach ($path in $Paths) {
		Select-String -Path $path -Pattern $Pattern
	}
	if ($matches) {
		$matches | ForEach-Object { Write-Error "$($_.Path):$($_.LineNumber): $($_.Line)" }
		throw "$Check failed."
	}
	Write-Host "PASS: $Check"
}

$mainFiles = @(Get-ChildItem -Path (Join-Path $repositoryRoot 'src\main') -Recurse -Filter '*.java' |
	ForEach-Object { $_.FullName })
$testFiles = @(Get-ChildItem -Path (Join-Path $repositoryRoot 'src\test') -Recurse -Filter '*.java' |
	ForEach-Object { $_.FullName })

if ($mainFiles.Count -eq 0 -or $testFiles.Count -eq 0) {
	throw 'Production and test Java sources must both exist.'
}

Write-Host 'Running Maven TestNG suite...'
& mvn clean test
if ($LASTEXITCODE -ne 0) {
	throw "mvn clean test failed with exit code $LASTEXITCODE."
}

Write-Host 'Checking approved source constraints...'
Assert-NoSourceMatches '\b(double|float|Double|Float)\b' $mainFiles 'BigDecimal-only monetary source scan'
Assert-NoSourceMatches 'java\.util\.(Date|Calendar)' $mainFiles 'LocalDate-only source scan'
Assert-NoSourceMatches 'java\.(nio|sql|net)\.|java\.io\.(?!PrintStream)' $mainFiles 'No persistence, database, or network APIs'
Assert-NoSourceMatches 'System\.exit' $mainFiles 'Normal return-based application exit'
Assert-NoSourceMatches 'import\s+(javax\.swing|java\.awt|javafx|java\.util\.logging|org\.slf4j|org\.apache\.log4j|org\.apache\.logging)' $mainFiles 'Console-only and no logging framework'
Assert-NoSourceMatches 'org\.junit' $testFiles 'TestNG-only test source scan'

$valueModels = @(
	'Transaction.java',
	'BudgetKey.java',
	'CategorySpending.java',
	'BalanceSummary.java',
	'BudgetAlert.java',
	'TransactionResult.java'
)
foreach ($fileName in $valueModels) {
	$file = Join-Path $repositoryRoot "src\main\java\org\capstone\expense\model\$fileName"
	if (-not (Select-String -Path $file -Pattern 'import lombok\.')) {
		throw "Lombok annotation requirement missing from $fileName."
	}
}
Write-Host 'PASS: Lombok is used on all six approved model value classes'

$mandatoryTests = @(
	'addExpenseRejectsZeroAmount',
	'addExpenseRejectsNegativeAmount',
	'findByIdNonExistentThrows',
	'listByDateRangeStartAfterEndThrows',
	'editTypeChangeExpenseToIncome',
	'setBudgetOverwritesExistingBudget',
	'alertFiresAtExactly100Percent'
)
$testContent = Get-Content -Path $testFiles
foreach ($testName in $mandatoryTests) {
	if (-not ($testContent | Select-String -SimpleMatch $testName)) {
		throw "Required dedicated test is missing: $testName."
	}
}
Write-Host 'PASS: all seven required edge-case test methods are present'

Write-Host 'All automated verification checks passed.'
