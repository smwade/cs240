# lab 2



## Trie Class

---

`class Trie implements ITrie`

- root
  
- nodeCount
  
- wordCount
  
  *Methods*
  
  ``` java
   @override
   public String toString(){
   }
   public boolean equals(Object o){
     if o == null:
       false
     if getClass() != o.getClass(){
       false
     }
     Trie that = (Trie) o;
   }
   public int hashCode() {
  
   }
  ```

}

## TrieNode

---

- count- TrieNode[]
  
  Delimiter = "[^a-zA-Z]"
  
  root is in the nodeCout
  
- `EditDistances()`

``` java
public String toString(){
    StringBuilder word = new StringBuilder();
    StringBuilder output = new StringBuilder();
    toStringHelper(root, word, output);
    return output.toString()
}

private void toStringHelper(TrieNode n, StringBuilder word, StringBuilder output){
    if (n == null){
        return;
    }
    if (n.getValue() > 0){
        // print word
        output.append(word.toString() + "\n");

    }
    for (int i = 0; i < 26; i++){
        word.append('a' + i);
        toStringHelper(n.getChild(i), word, output);
        word.setLength(word.getLength() - 1);
    }
}

public equals(){
    //compare same borring address bizz
    //compare node and word count
    //2 nodes as imput
    //recurse down at the same time
    //maybe put equal method on Node
}

public hashCode(){
    nodeCout * rootCount * prime;
}
```



``` java
public String toString(){
    StringBuilder word = new StringBuilder();
    StringBuilder output = new StringBuilder();
    toStringHelper(root, word, output);
    return output.toString()
}

private void toStringHelper(TrieNode n, StringBuilder word, StringBuilder output){
    if (n == null){
        return;
    }
    if (n.getValue() > 0){
        // print word
        output.append(word.toString() + "\n");

    }
    for (int i = 0; i < 26; i++){
        word.append('a' + i);
        toStringHelper(n.getChild(i), word, output);
        word.setLength(word.getLength() - 1);
    }
}

public equals(){
    //compare same borring address bizz
    //compare node and word count
    //2 nodes as imput
    //recurse down at the same time
    //maybe put equal method on Node
}

public hashCode(){
    nodeCout * rootCount * prime;
}
```





