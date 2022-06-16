**Input API:** Oxford Dictionaries API
<br>
**Output API:** Pastebin API
<br>
**Claimed Tier:** Distinction
<br>
_Credit Optional Feature 1:_ Theme song
<br>
_Credit Optional Feature 2:_ About feature
<br>
_Distinction Optional Feature:_ Spinning progress indicator
<br>
_High Distinction Optional Feature:_ 

**Milestone 1 Submission:**
```
SHA: 94ff44908aa19e79d2e969dc402283259d268c34
URI: https://github.sydney.edu.au/rton8097/SCD2_2022/commit/94ff44908aa19e79d2e969dc402283259d268c34
```

**Milestone 1 Re-Submission:**
```
SHA: 94ff44908aa19e79d2e969dc402283259d268c34
URI: https://github.sydney.edu.au/rton8097/SCD2_2022/commit/94ff44908aa19e79d2e969dc402283259d268c34
```
**Milestone 2 Submission:**
```
SHA: 7fa257e577e3954ae3cc528ee1fa90dd1eab0173
URI: https://github.sydney.edu.au/rton8097/SCD2_2022/commit/7fa257e577e3954ae3cc528ee1fa90dd1eab0173
```
**Milestone 2 Re-Submission:**
```
SHA: 7fa257e577e3954ae3cc528ee1fa90dd1eab0173
URI: https://github.sydney.edu.au/rton8097/SCD2_2022/commit/7fa257e577e3954ae3cc528ee1fa90dd1eab0173
```
**Exam Base Commit:**
```
SHA: 7fa257e577e3954ae3cc528ee1fa90dd1eab0173
URI: https://github.sydney.edu.au/rton8097/SCD2_2022/commit/7fa257e577e3954ae3cc528ee1fa90dd1eab0173
```
**Exam Submission Commit:**
```
SHA: 0f1b3a765d60f03862e2e727e3da5fbc625b294a
URI: https://github.sydney.edu.au/rton8097/SCD2_2022/commit/0f1b3a765d60f03862e2e727e3da5fbc625b294a
```

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

To use the `online` engines, it requires the environment variables to be set. 
The `offline` engines can be used without the variables being set.

# Quirks
- To search a synonym or antonym, you need to double-click on the **number** 
of synonym or antonym. Not the id or text as that will not search the word.

- When choosing a lemma to search for the entry, the lemmas listed includes both
English languages (EN-GB and EN-US). With no specific language provided by 
the api for the lemmas, the application will use the language selected 
when searching for an entry. This leads to some lemmas leading to the entry 
not being found.

- To use the menu bar buttons, you need to click on the text.

# References
- [Oxford Dictionaries API](https://developer.oxforddictionaries.com/)
- [Pastebin API](https://pastebin.com/doc_api)
- Theme song: [dreamy night - LilyPichu](https://www.youtube.com/watch?v=DXuNJ267Vss)
- Loading GIF: [Loading.io](https://loading.io/)