
Extract contents of this zip file to a folder of your choice. A subfolder named exchange v0.1.1.b will be created.

You should set up a new eclipse workspace and import all projects from the folder created above. There will be four projects named api,core,console and web.

All projects can be built by opening a windows command window to exchange v0.1.1.b folder and typeing "mvn package".  This will compile and run JUnit tests 
and package a stand alone application as well a web application.

To run stand alone application  type runApp.cmd from the folder exchange v0.1.1.b (after running "mvn package".  Application will start with 100 coins each of denominations
50,25,10,5 and 1 cents. The startup set up can be modified by invoking runApp with command line arguments --fiftys=200 --quarters=300 --dimes=10 --nickels=3000 --cents=1000 (these are case sensitive

to run web app type runWebApp.cmd  from the same folder as above. 