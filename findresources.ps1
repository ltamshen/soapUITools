# This test will search each soapUI test case and compile a list of testcases that use the given resource

$Path = "D:\Data\Project\*"
$Text = "endpoint goes here"
$Results = "C:\temp\TestsUsingThisResource.txt"

Get-ChildItem $Path -recurse | Select-String -pattern $Text | group path | select name > $Results
