*Gerbera* is a Bitcoin offline transaction builder used to generate a raw hex Bitcoin transaction.
Generated transaction hex may be later broadcast via online services (e.g. [Blockchain.info Broadcast](https://blockchain.info/ru/pushtx)).

## What's Supported
* `SIGHASH_ALL` signature type only
* `P2PKH` transaction type only
* Multiple inputs from different addresses (signed with different keys)
* Multiple outputs
* Regular and compressed addresses

## Code examples
### Build multiple-outputs transaction

The following is an example of how to build a transaction with one input and two outputs.

```java
Transaction transaction = TransactionBuilder.create()
                .from(
                        "e034cda5a310c187daf34e553c68470b0ec04335da37e2bb8c714481a3003570", // from transaction big endian
                        2,                                                                  // from Tout index
                        "76a91444aeddf5d5f4f8fccf68ecd8e83242f6cfb6b9fa88ac",               // locking script
                        5000)                                                               // satoshi
                .signedWithWif("5Hy[--------------HIDDEN--------------]9rx")                // WIF for previously added inputs
                .to("15r9m5rTho4NaTzC5GiXqHMGQ4Lst8XXJD", 1700)                             // output. where and how much (satoshi)
                .to("1HaUAMnraw9VWHGaovtzpYgRMuGfUWNdZA", 2000)                             // another output
                .withFee(300)                                                               // fee (satoshi)
                .changeTo("17GAS26XPkzzoow5a79xGKszW7tRfiS4JV")                             // Send change to (if any)
                .build();
```
The `signWithWif()` method applies given private key to all the inputs added after last call of the `signWithWif()` method.

If **(all inputs) - (all outputs) - fee** gives positive value, it will be send to `changeTo` address.

### Build multiple-inputs transaction

The following is an example of how to build a transaction with two inputs and one output.

```java
Transaction transaction = TransactionBuilder.create()
                .from(
                        "bfd8a354f0ca7ccb1a59591f9a0ce08a5728babc5b74c993099e3e3c84d98296", // from transaction big endian
                        0,                                                                  // from Tout index
                        "76a914352c32a2b6ee5aa173334b6df32edd4117489a9a88ac",               // locking script
                        1700                                                                // satoshi
                )
                .signedWithWif("L4U[--------------HIDDEN--------------]xUh")                // WIF for previously added inputs
                .from(
                        "bfd8a354f0ca7ccb1a59591f9a0ce08a5728babc5b74c993099e3e3c84d98296", // from transaction big endian
                        1,                                                                  // from Tout index
                        "76a914b5d66995560fb4ea489c6d70e733537288f0cdcd88ac",               // locking script
                        2000                                                                // satoshi
                )
                .signedWithWif("5KN[--------------HIDDEN--------------]8hf")                // WIF for previously added inputs
                .to("16BZ2txvJzywXtTxah7jVEZzerMpQT5ArH", 1000)                             // output. where and how much (satoshi)
                .withFee(2700)                                                              // fee (satoshi)
                .build();
```

In case change address is not explicitly included (like in this example), but change is present,
an `Exception` containing change satoshi amount is thrown. Change address must be explicitly declared so that change satoshi are not accidentally spent as fee.

### Get transaction hex

The built transaction object may be used to get raw transaction hex:
```java
transaction.getRawTransaction()
```
The output is a `String` that may be submitted to an online service to broadcast the built transaction.

Result example:
```
0100000001703500a38144718cbbe237da3543c00e0b47683c554ef3da87c110a3a5cd34e0020000008b483045022100c492d67d7e0344e8e34acabcb37a8f39ee78ac2cc6c3ce8a988bc90208231bae02203bcdc8811cdc8d50debdbe40614021578d245d0ada475b765c12e639f7cceeb7014104df661fe5f879a80bf362a13e6d50de70fda2eddca7d0643641d08f80c162669984547d59eb68c1dfd911e8ef64a9f2f9c8ccfe0ae206845c84c2df581ad51ca1ffffffff03a4060000000000001976a914352c32a2b6ee5aa173334b6df32edd4117489a9a88acd0070000000000001976a914b5d66995560fb4ea489c6d70e733537288f0cdcd88ace8030000000000001976a91444aeddf5d5f4f8fccf68ecd8e83242f6cfb6b9fa88ac00000000
```
> Note. The example hex is already broadcast. Submitting this transaction will give an error.

### Get transaction field-split representation

The transaction object may be also used to get field-split hex transaction representation that makes reading raw hex easier.
```java
transaction.getSplitTransaction()
``` 

Result example:
```
Version             01000000
Input count         01
Input #1
   Transaction out  703500a38144718cbbe237da3543c00e0b47683c554ef3da87c110a3a5cd34e0
   Tout index       02000000
   Unlock length    8b
   Unlock           483045022100c492d67d7e0344e8e34acabcb37a8f39ee78ac2cc6c3ce8a988bc90208231bae02203bcdc8811cdc8d50debdbe40614021578d245d0ada475b765c12e639f7cceeb7014104df661fe5f879a80bf362a13e6d50de70fda2eddca7d0643641d08f80c162669984547d59eb68c1dfd911e8ef64a9f2f9c8ccfe0ae206845c84c2df581ad51ca1
   Sequence         ffffffff
Output count        03
Output #1 (Custom)
   Satoshi          a406000000000000
   Lock length      19
   Lock             76a914352c32a2b6ee5aa173334b6df32edd4117489a9a88ac
Output #2 (Custom)
   Satoshi          d007000000000000
   Lock length      19
   Lock             76a914b5d66995560fb4ea489c6d70e733537288f0cdcd88ac
Output #3 (Change)
   Satoshi          e803000000000000
   Lock length      19
   Lock             76a91444aeddf5d5f4f8fccf68ecd8e83242f6cfb6b9fa88ac
Locktime            00000000
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