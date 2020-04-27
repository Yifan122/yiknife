path: demos/main/java

compile:
```shell script
javac basic/bytecode/ByteCode.java
```

run
```shell script
java basic.bytecode.ByteCode
```

decompile
```shell script
javap -c  basic/bytecode/ByteCode.class
javap -verbose  basic/bytecode/ByteCode.class
```

decompile from a jar
```shell script
javap -verbose -claspath com.ioomc.ByreCode yiknife.jar
```