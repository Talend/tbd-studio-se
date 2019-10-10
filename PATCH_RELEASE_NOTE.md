---
version: 7.2.1
module: https://talend.poolparty.biz/coretaxonomy/42
product: https://talend.poolparty.biz/coretaxonomy/183

---

# TPS-3243 <!-- mandatory -->

| Info             | Value |
| ---------------- | ---------------- |
| Patch Name       | Patch\_20191010\_TPS-3459\_v1\_7.2.1 |
| Release Date     | 2019-10-14 |
| Target Version   | 20190620\_1446-7.2.1 |
| Product affected | Talend Studio |

## Introduction <!-- mandatory -->

This patch is cumulative. It includes all previous generally available patches for Talend Studio 7.2.1.

**NOTE**: To download this patch, liaise with your Support contact at Talend.

## Fixed issues <!-- mandatory -->

This patch contains the following fixes:

- TPS-3459 [7.2.1] Change Databricks API calls (for DBR 5.4)

This patch also includes the following patches:

- TPS-3243 [7.2.1] Backporting the support of Databricks 5.x and ADLS Gen 2 (TBD-7856) + Spark Batch fixes (TBD-8836, TBD-8850) + Configurable poll interval for Databricks job status (TBD-9006)
- TPS-3287 [7.2.1] Cannot add more than 5 columns in tAggregateRow (TBD-8860)
- TPS-3273 [7.2.1] Error in calling spark job from trunjob job inside standard job (TUP-23950)
- TPS-3249 [7.2.1] Regression caused by TUP-21532 for ESB use case using tRunJob (TUP-23755)
- TPS-3263 [7.2.1] Studio changes in column order are not saved (no propagate changes dialog) (TUP-23809)
- TPS-3268 [7.2.1] ClassNotFoundException of the class of the job called by tRunJob in Routes (TESB-26048)
- TPS-3269 [7.2.1] [Java 11] Failures when publishing to cloud using CI (maven plugin) (TESB-26461)
- TPS-3270 [7.2.1] Duplicated libraries in private & import packages of the build manifest file (TESB-26293)
- TPS-3294 [7.2.1] USAF Case/Jira Status- Case 00144616 (TUP-23087 and TUP-21518)

## Prerequisites <!-- mandatory -->

Consider the following requirements for your system:

- Talend Studio 7.2.1 must be installed.
- In the "{Studio_Home}/plugin" folder, remove the Databricks plugin. The file name of this plugin could look like 
    org.talend.hadoop.distribution.databricks_7.2.1.20190613_2004
- In the "{Studio_Home}/configuration/config.ini" file, add the following entry: 
    org.talend.hadoop.distribution.dbr350@start,org.talend.hadoop.distribution.dbr540@start
- Clean the libraries installed on the Databricks cluster. To do this, 
   1. On the cluster side, click the "Libraries" tab and then select the check box to select all the libraries.
   2. Click "Uninstall".
   3. Restart the cluster.
- To make TPS-3269 totally work, please replace the cloudpublisher-maven-plugin-7.2.1.jar into "{Studio_Home}/configuration/.m2/repository/org/talend/ci/cloudpublisher-maven-plugin/7.2.1" from "repository/org/talend/ci/cloudpublisher-maven-plugin/7.2.1" in patch zip.

## Installation <!-- mandatory -->

<!--
- Detailed installation steps for the customer.
- If any files need to be backed up before installation, it should be mentioned in this section.
- Two scenarios need to be considered for the installation:
 1. The customer has not yet installed any patch before => provide instructions for this
 2. The customer had installed one previous cumulative patch => provide instructions for this
-->

### Installing the patch using Software update <!-- if applicable -->

1) Logon TAC and switch to Configuration->Software Update, then enter the correct values and save referring to the documentation: https://help.talend.com/reader/f7Em9WV_cPm2RRywucSN0Q/j9x5iXV~vyxMlUafnDejaQ

2) Switch to Software update page, where the new patch will be listed. The patch can be downloaded from here into the nexus repository.

3) On Studio Side: Logon Studio with remote mode, on the logon page the Update button is displayed: click this button to install the patch.

### Installing the patch using Talend Studio <!-- if applicable -->

1) Create a folder named "patches" under your studio installer directory and copy the patch .zip file to this folder.

2) Restart your studio: a window pops up, then click OK to install the patch, or restart the commandline and the patch will be installed automatically.

### Installing the patch using Commandline <!-- if applicable -->

Execute the following commands:

1. Talend-Studio-win-x86_64.exe -nosplash -application org.talend.commandline.CommandLine -consoleLog -data commandline-workspace startServer -p 8002 --talendDebug
2. initRemote {tac_url} -ul {TAC login username} -up {TAC login password}
3. checkAndUpdate -tu {TAC login username} -tup {TAC login password}