###########################################################
# This script finds the list of soapUI test cases calling the test indicated by $Test and saves it to the file indicated by $Results
# Note: this will not find any tests that are called from Groovy scripts. To do that, search the xml for "RunTest" and the test name.

#Set-ExecutionPolicy RemoteSigned
$Path = "D:\Data\Project\*"
$Test = "e0d81fcf-3b8b-4311-af0f-da4c1ae3f812"
$Results = "C:\temp\test.txt"
$Results1 = "C:\temp\test1.txt"


Get-ChildItem $Path -recurse | Select-String -pattern "calltestcase" | Select-String -pattern $Test  | group path |select name > $Results
Get-ChildItem $Path -recurse | Select-String -pattern "RunTest" | Select-String -pattern $Test  | group path |select name > $Results1

