$tempStorageAccountName ="cadimxbis01"
$tempDescription = "Storage for hdinsight"
$tempLabel = "cadimxbis01"
$tempLocation = "East US"
$tempStorageContainerName="hdinsightest"
$tempPermission="Container"
$filejarpath= "C:\xxx.jar"
$filedatapath="C:\data\xxx.csv"
$clusterName = "cadIMXBIh01"     
$clusterNodes = 1 

#start function createStorageAccount
Function createUpdateStorageAccount{

  	Param($tempStorageAccountName, $tempDescription,$tempLabel,$tempLocation) 
	
	#First check if storage account is already existing
	$strStorageAccount= Get-AzureStorageAccount|select storageAccountName
	$storageAccountFlag = "false"
	foreach ($objItem in $strStorageAccount) {	    	    
	 if($objItem.StorageAccountName -eq $tempStorageAccountName){
	   write-host "$objItem storage already exist.....Press key to continue : Storage AccountName: $tempStorageAccountName"
	   $storageAccountFlag = "true"
	 }	 
	}
	
	#If not then create the storage account
	#Else update label and description of storageAccount
	if($storageAccountFlag -eq "false"){
	   New-AzureStorageAccount -StorageAccountName $tempStorageAccountName -Description $tempDescription -Label $tempLabel -Location $tempLocation
	}else{
	  Set-AzureStorageAccount  -StorageAccountName $tempStorageAccountName -Description $tempDescription
	}
	
}

#start function createUpdateStorageContainer
Function createUpdateStorageContainer{

  	Param($tempstorageContainerName,$temppermission) 

	$storageAccountKey = Get-AzureStorageKey $tempStorageAccountName | %{ $_.Primary }
	$storageContext=New-AzureStorageContext -StorageAccountName $tempStorageAccountName -StorageAccountKey $storageAccountKey	
	$storageContainerFlag = "false"
	
	try{
	     #First check if storage container is already existing
	     $strStorageContainer=  Get-AzureStorageContainer -Name  $tempStorageContainerName -Context $storageContext
	     $storageContainerFlag = "true"
	    }catch{
	       $storageContainerFlag = "false"
	    }
	
	#If not then create the storage account
	#Else update label and description of storageAccount
	if($storageContainerFlag -eq "false"){
	   write-host "I have come here $storageContainerFlag"
	   New-AzureStorageContainer -Name $tempStorageContainerName -Permission $tempPermission  -Context $storageContext
	}else{
	  write-host "I have come in else $storageContainerFlag"
	  Set-AzureStorageContainerAcl  -Name $tempStorageContainerName -Permission  $tempPermission -Context $storageContext
	}
	
}
#end function createUpdateStorageContainer

#begin function uploadFiletoContainer
Function uploadFiletoContainer{

Param($filedatapath,$filejarpath)
$storageAccountKey = Get-AzureStorageKey $tempStorageAccountName | %{ $_.Primary }
$storageContext=New-AzureStorageContext -StorageAccountName $tempStorageAccountName -StorageAccountKey $storageAccountKey
Set-AzureStorageBlobContent -File $filedatapath -Container $tempStorageContainerName -Context $storageContext
Set-AzureStorageBlobContent -File $filejarpath -Container $tempStorageContainerName -Context $storageContext

}
#end function uploadFiletoContainer


#end function createHdinsightCluster
Function createHdinsightCluster{

Param($clusterName,$clusterNodes)

$storageAccountKey = Get-AzureStorageKey $tempStorageAccountName | %{ $_.Primary }
$clusterAccountFlag = "false"

#First check if cluster existing
$clusterhdinsight =Get-AzureHDInsightCluster|select Name

foreach($ObjItem in $clusterhdinsight){
 if($objItem.Name -eq $clusterName){
   $clusterAccountFlag = "true"
   write-host "I am inside $objItem.Name "
 } 
}

if($clusterAccountFlag -eq "false"){ 
	New-AzureHDInsightCluster -Name $clusterName -Location $tempLocation -DefaultStorageAccountName "$tempStorageAccountName.blob.core.windows.net" -DefaultStorageAccountKey $storageAccountKey -DefaultStorageContainerName $tempStorageContainerName  -ClusterSizeInNodes $clusterNodes
}else{
  write-host "I am inside $clusterAccountFlag "
}

}
#end function createHdinsightCluster

#begin function executing hdinsight job
Function executeHdinsightJob{

$wordCountJobDefinition = New-AzureHDInsightMapReduceJobDefinition -JarFile "wasb:///hadoopSample.jar" -ClassName "com.uvwea.hadoop.UserItemDriver" -Arguments "wasb:///BugsBunnyFPDashboardTemplateDataSet.csv", "wasb:///output"
$wordCountJob = Start-AzureHDInsightJob -Cluster $clusterName -JobDefinition $wordCountJobDefinition | Wait-AzureHDInsightJob -WaitTimeoutInSeconds 3600
Get-AzureHDInsightJobOutput -Cluster $clusterName -JobId $wordCountJob.JobId -StandardError

Remove-AzureHDInsightCluster -Name  $clusterName

}
#end function executing hdinsight job

#start function createStorageAccount
createUpdateStorageAccount -tempStorageAccountName $tempStorageAccountName  -tempDescription $tempDescription -tempLabel $tempLabel -tempLocation $tempLocation
createUpdateStorageContainer  -tempStorageContainerName $tempStorageContainerName -tempPermission $tempPermission 
uploadFiletoContainer -filedatapath $filedatapath -filejarpath $filejarpath
createHdinsightCluster -clusterName $clusterName -clusterNodes $clusterNodes
executeHdinsightJob