function removeCookies() {
  var res = document.cookie;
  var multiple = res.split(";");
  for(var i = 0; i < multiple.length; i++) {
  var key = multiple[i].split("=");
   document.cookie = key[0]+" =; expires = Thu, 01 Jan 1970 00:00:00 UTC";
  }
}

var d = document;

var name;
var initials;
var posada;

function addRow()
{
    // Считываем значения с формы
    status = d.getElementById('status').value;

    // Находим нужную таблицу
    var tbody = d.getElementById('table').getElementsByTagName('TBODY')[0];

    // Создаем строку таблицы и добавляем ее
    var row = d.createElement("TR");
    tbody.appendChild(row);

    // Создаем ячейки в вышесозданной строке
    // и добавляем тх
    var td1 = d.createElement("TD");

    row.appendChild(td1);

    // Наполняем ячейки
    td2.innerHTML = status;
}