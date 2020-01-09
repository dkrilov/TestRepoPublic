# JmeterPOC

## Script execution Command 


jmeter -f -t (ScriptName) -n -l Result/Output.csv


## Passing paramter (Example lime Token)


jmeter -f -t SO_CorpBonds_SecurityInformation_SoapUI.jmx -JStage=QA -JToken=6b45a46653b86d6f  -n -l Results.csv
 
