#define JsonFile ReadIni(AddBackslash(SourcePath) + "config.json")
#define ExePath JsonGet(JsonFile, "paths/exePath")
#define IconPath JsonGet(JsonFile, "paths/iconPath")
#define OutputDir JsonGet(JsonFile, "paths/outputDir")

[Setup]
AppName=Haxly
AppVersion=1.0
DefaultDirName={#OutputDir}
DefaultGroupName=Haxly
OutputBaseFilename=HaxlySetup

[Files]
Source: "{#ExePath}"; DestDir: "{app}"; Flags: ignoreversion
Source: "{#IconPath}"; DestDir: "{app}"; Flags: ignoreversion

[Registry]
Root: HKCR; Subkey: "*\shell\HashAnalyzer"; ValueType: string; ValueName: ""; ValueData: "Calculate the hash"; Flags: createvalueifdoesntexist
Root: HKCR; Subkey: "*\shell\HashAnalyzer\command"; ValueType: string; ValueName: ""; ValueData: """{app}\Haxly.exe"" ""%1"""; Flags: createvalueifdoesntexist
Root: HKCR; Subkey: "*\shell\HashAnalyzer"; ValueType: string; ValueName: "Icon"; ValueData: """{app}\Haxly_logo.ico"""; Flags: createvalueifdoesntexist

[Tasks]
Name: "desktopicon"; Description: "Add Haxly to the desktop"; GroupDescription: "Additional Options"; Flags: unchecked

[Icons]
Name: "{group}\Haxly"; Filename: "{app}\Haxly.exe"; IconFilename: "{app}\Haxly_logo.ico"
Name: "{commondesktop}\Haxly"; Filename: "{app}\Haxly.exe"; Tasks: desktopicon; IconFilename: "{app}\Haxly_logo.ico"

[Run]
Filename: "{app}\Haxly.exe"; Description: "Run Haxly"; Flags: nowait postinstall
