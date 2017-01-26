findtests.ps1 - This script finds the list of soapUI test cases calling the test indicated by $Test and saves it to the file indicated by $Results
_________________

Assumptions: 

1- The local copy of the soapUI tests are located here:
D:\Data\Project

2- There exists a folder C:\temp

To use:
Edit the script and change the $Test variable to the guid of the test case you are looking for.
To find the guid, open the .xml for the test and copy the "id" value (eg. id="71890ea4-2330-4f4f-9ee1-6cc1a750190a").

Remember to open a powershell session and Set-ExecutionPolicy RemoteSigned before running.

NOTE: This is still under test.



FindCallingTests.jar <testname> - creates a file containing all of the test cases that call this test
_________________

Assumptions:
Same as above.


1 - run this from the command line.
2 - this takes one parameter which is the name of the test case

NOTE: not much error handling yet.


findresources.ps1 - This script will search each soapUI test case and compile a list of testcases that use the given resource
_________________

Same assumptions as above.

To use:
Edit the script and change the $Text variable to the name of the resource you are looking for.
Remember to open a powershell session and Set-ExecutionPolicy RemoteSigned before running.


FindResourcesUpdated.jar - creates a report containing each test case and lists the REST endpoints it calls
__________________________

Same assumptions as above.
Creates a file named Resources.txt (which can be pasted into Excel for easier viewing).

