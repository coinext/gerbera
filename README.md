*Gerbera* is a Bitcoin offline transaction builder used to generate a raw hex Bitcoin transaction.
Generated transaction hex may be later broadcast via online services (e.g. [Blockchain.info Broadcast](https://blockchain.info/ru/pushtx)).

## What's Supported
* Send to:
    * `P2PKH` (1-Addresses. Regular and compressed)
    * `P2SH` (3-Addresses, including SegWit)
* Spend from:
    * `P2PKH`-produced outputs (1-Addresses)
* Signature types
    * `SIGHASH_ALL`
* Multiple inputs from different addresses (signed with different keys)
* Multiple outputs
* Networks
    * MainNet
    * TestNet

## Next to implement
* Spend from `P2SH-P2WPKH` SegWit

## Code examples
### Build multiple-outputs transaction

The following is an example of how to build a transaction with one input and two outputs.

```java
Transaction transaction = TransactionBuilder.create()
        .from(
                "fb8212db8c6c0509d1892115b0a73acfc3c30668a0f51dc80758cc479e29e67e", // from transaction big endian
                13,                                                                 // from Tout index
                "76a91491b24bf9f5288532960ac687abb035127b1d28a588ac",               // locking script
                40404                                                               // satoshi
        )
        .signedWithWif("5HpHagT65TZzG1PH3CSu63k8DbpvD8s5ip4nEB3kEsreAnchuDf")       // WIF for previously added inputs
        .to("1NZUP3JAc9JkmbvmoTv7nVgZGtyJjirKV1", 17000)                            // output. where and how much (satoshi)   
        .to("31h38a54tFMrR8kvVig3R23ntQMoitzkAf", 20000)                            // another output
        .withFee(3000)                                                              // fee (satoshi)
        .changeTo("17Vu7st1U1KwymUKU4jJheHHGRVNqrcfLD")                             // Send change to (if any)
        .build();
```
The `signWithWif()` method applies given private key to all the inputs added after last call of the `signWithWif()` method.

If **(all inputs) - (all outputs) - fee** gives positive value, it will be send to `changeTo` address.

### Build multiple-inputs transaction

The following is an example of how to build a transaction with three inputs and one output.
Two inputs use the same private key and the third is different:

```java
Transaction transaction = TransactionBuilder.create()
        .from(
                "3722df3eff476fe31db8ae2b52806379bde58e9289026d10e425adc25c2e3f03", // from transaction big endian
                1,                                                                  // from Tout index
                "76a91491b24bf9f5288532960ac687abb035127b1d28a588ac",               // locking script
                3000                                                                // satoshi
        )
        .signedWithWif("5HpHagT65TZzG1PH3CSu63k8DbpvD8s5ip4nEB3kEsreAnchuDf")       // WIF for previously added inputs
        .from(
                "262dc0cba867ba38ec5e92239acbe3074eea3a79105b8e551f60905b1cd79abe", // from transaction big endian
                2,                                                                  // from Tout index
                "76a91406afd46bcdfd22ef94ac122aa11f241244a37ecc88ac",               // locking script
                2500                                                                // satoshi
        )
        .from(
                "8b109e31552b7bd3a9c41a30c739eeb0ab90a6ffc92f5a9471e0874acd75e5ba", // from transaction big endian
                12,                                                                 // from Tout index
                "76a91406afd46bcdfd22ef94ac122aa11f241244a37ecc88ac",               // locking script
                10000                                                               // satoshi
        )
        .signedWithWif("KwDiBf89QgGbjEhKnhXJuH7LrciVrZi3qYjgd9M7rFU74NMTptX4")      // WIF for previously added inputs
        .to("1NZUP3JAc9JkmbvmoTv7nVgZGtyJjirKV1", 10000)                            // fee (satoshi)
        .withFee(5500)
        .build();
```

In case change address is not explicitly included (like in this example), but change is present,
an `Exception` containing change satoshi amount is thrown. Change address must be explicitly declared so that change satoshi are not accidentally spent as fee.

Transaction may contain multiple inputs and multiple outputs at the same time.

### Build transaction for TestNet

To build a TestNet transaction, create a `TransactionBulder` with `false` argument (`true` is for MainNet, which is default)
```java
Transaction transaction = TransactionBuilder.create(false)
        ...
        ...
        ...
        .build();
```
Note that in TestNet, addresses and WIFs have different prefixes.

### Get transaction hex

The built transaction object may be used to get raw transaction hex:
```java
transaction.getRawTransaction()
```
The output is a `String` that may be submitted to an online service to broadcast the built transaction.

Result example:
```
01000000017ee6299e47cc5807c81df5a06806c3c3cf3aa7b0152189d109056c8cdb1282fb0d0000008b483045022100a71f2610b30934545ffb6fc37af4cc8384523ef0aefbc90d47f148155aedccd902206b8efdddfb0bc39c4047e05d47ad6a80efaffee0f54fd77f26fbcce1ce80706001410479be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8ffffffff0368420000000000001976a914ec7eced2c57ed1292bc4eb9bfd13c9f7603bc33888ac204e0000000000001976a914c42e7ef92fdb603af844d064faad95db9bcdfd3d88ac94010000000000001976a9144747e8746cddb33b0f7f95a90f89f89fb387cbb688ac00000000
```


### Get transaction field-split representation

The transaction object may be also used to get field-split hex transaction representation that makes reading raw hex easier.
```java
transaction.getSplitTransaction()
``` 

Result example:
```
Version                  01000000
Input count              01
   Input
      Transaction out    7ee6299e47cc5807c81df5a06806c3c3cf3aa7b0152189d109056c8cdb1282fb
      Tout index         0d000000
      Unlock length      8a
      Unlock             47304402206fb7d70259301222232e8e006cf1d0c6814666e52b5379e04b7cef0d695c4ff202205323e65a65bd74d904d02dfa3d33bfd1d937cd82fadf1a41d9da9d288498163a01410479be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8
      Sequence           ffffffff
Output count             03
   Output (Custom)
      Satoshi            6842000000000000
      Lock length        19
      Lock               76a914ec7eced2c57ed1292bc4eb9bfd13c9f7603bc33888ac
   Output (Custom)
      Satoshi            204e000000000000
      Lock length        17
      Lock               a914000102030405060708090001020304050607080987
   Output (Change)
      Satoshi            9401000000000000
      Lock length        19
      Lock               76a9144747e8746cddb33b0f7f95a90f89f89fb387cbb688ac
Locktime                 00000000
```

### Get additional transaction information
To get additional transaction info (size in bytes and satoshi/byte fee) call the following method of the `transaction` object:
```java
transaction.getTransactionInfo()
``` 

Result example:
```
Size (bytes):
  292
Fee (satoshi/byte):
  10.274
```

## How to compile
First clone the project:
```sh
$ git clone https://github.com/aafomin/gerbera.git
```

Then build a jar:
```sh
$ ./gradlew build
```
The jar `gerbera-VERSION.jar` will appear in `./build/libs/`

## How to use
Add the built jar to your project and start from `sd.fomin.gerbera.transaction.TransactionBuilder` class.
