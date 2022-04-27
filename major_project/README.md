# How to run

The program takes 2 arguments in order to run the application.

The format: 
```properties
gradle run --args="arg1 arg2"
```

`arg1` refers to the **InputEngine**. It will only allow `online` and `offline`.

`arg2` refers to the **OutputEngine**. It will only allow `online` and `offline`.

Example use of online InputEngine and offline OutputEngine:
```properties
gradle run --args="online offline"
```

Running the application with no arguments or arguments other than `online` or `offline` will not work.

# Quirks
- To search a synonym or antonym, you need to double-click on the number 
of synonym or antonym. Not the id or text as that will not search the word.

- When choosing a lemma to search for the entry, the lemmas listed includes both
English languages (EN-GB and EN-US). With no specific language provided by 
the api for the lemmas, the application will use the language selected 
when searching for an entry. This leads to some lemmas leading to the entry 
not being found.

# Level of Implementation
- Pass