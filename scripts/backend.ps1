param(
    [ValidateSet('run', 'verify', 'version')]
    [string]$Task = 'run'
)

$ErrorActionPreference = 'Stop'
$jdkPath = $env:JAVA_HOME
if (-not $jdkPath -or -not (Test-Path -LiteralPath (Join-Path $jdkPath 'bin/javac.exe'))) {
    throw 'Set JAVA_HOME to an installed JDK 21 directory, then open a new terminal.'
}

$compilerVersion = & (Join-Path $jdkPath 'bin/javac.exe') -version 2>&1
if ($LASTEXITCODE -ne 0 -or "$compilerVersion" -notmatch '^javac 21(?:\.|\s|$)') {
    throw 'Gatherly requires JDK 21. Check JAVA_HOME.'
}

$previousPath = $env:PATH
try {
    $env:PATH = (Join-Path $jdkPath 'bin') + [IO.Path]::PathSeparator + $previousPath
    Push-Location (Join-Path $PSScriptRoot '../backend')
    try {
        switch ($Task) {
            'version' { & .\mvnw.cmd -version }
            'verify' { & .\mvnw.cmd --batch-mode clean verify }
            'run' { & .\mvnw.cmd spring-boot:run }
        }
        $mavenExitCode = $LASTEXITCODE
    }
    finally {
        Pop-Location
    }
}
finally {
    $env:PATH = $previousPath
}

if ($mavenExitCode -ne 0) {
    throw "Maven failed with exit code $mavenExitCode."
}
