# com.aspira.yrakovets

Тестове завдання

Що не зроблено: лінки захардкожені, а не читаються з JS. Не зроблена обробка негативних сценаріїв.

Технічні рішення:
1. Я зробив парсинг лише потрібних полів. На мою думку це зробить программу трохи менш чутливою до змін у API. Також тому використав для парсингу не com.fasterxml.jackson.databind.ObjectMapper, а  com.google.gson.stream.JsonReader, оскільки читаю поля вибірково.


