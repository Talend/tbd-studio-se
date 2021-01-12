These files are required for customers that have conflicts with shaded hive-exec library. 

Order of generating them is similar to process of generation cdh611 built-in distribution: 
1) Generate dynamic distributions with required version
2) Update hive-exec shade library version
3) Commit to the git

As for usage in Studio just import the resulting file as a dynamic distribution.

Also, please make sure that required hive-exec shade version exists. 
All information about how to build it is in tbd-studio-ee/main/components_libs/talend-hive-exec-shade.

Dev Note: 
I think there are 2 ways to avoid this manual process: 
1) Get rid of shading approach
2) Implement parametrized distribution generation process, so cdh631 will require shaded hive-exec version for cdh631 with corresponding 3rd party dependencies. Also, it requires fro hive-exec shaded jars to be built and promoted for each supported cdh6x version.

