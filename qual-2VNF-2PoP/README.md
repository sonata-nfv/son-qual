# SONATA Qualification test 2VNF 1PoP service

This is the service that will be used in our 2VNF 1 PoP qualification test.

Service chain: 

```
              +-------------------+        +--------------------+       
  IN          |                   |        |                    |       OUT
      +------->       vRING       +-------->       vRING        +------->
              |                   |        |                    |       
              +-------------------+        +--------------------+       
```

## vRING VNF

The vRING consists of one part:

1. Open vSwitch-based data plane

The vRING add the following flows to OVS when boot:
```
ovs-ofctl add-flow br-ring in_port=1,actions=output:2
ovs-ofctl add-flow br-ring in_port=2,actions=output:1
```

