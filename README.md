# KeePocket2
Projeto PAS
![sf-01](https://user-images.githubusercontent.com/101331020/176289400-f1c3c31a-c329-4352-a8d7-1e63f2943ba6.png)


## Descrição do Projeto
Neste projeto foi desenvolvida uma aplicação com, o objetivo de gerir as despesas e o rendimento dos seus utilizadores, com o intuito de ajudar os seus utilizadores, e de lhes conseguir providenciar tempo extra para se preocuparem com outros problemas, assegurando assim a sua estabilidade financeira. Mediante este conjunto de app e web, pretendemos disponibilizar os dados em ambas as plataformas para facilitar a sua vizualização. 

## Elementos da Equipa
* Bruno Horta
* Dinis Dias
* Rodrigo Serafim

## Requisitos
### Requisitos Minimos Android
* Comunicação com API Laravel;
* Fazer sistema de login com database;
* Inserir dados;
* Utilização de repositório com LiveData e ViewModel;
* Room DataBase;

### Requisitos Web
* Login/ Registo na Web;
* Visualização de dados;
* Inserir dados;


### Requisitos Avançados Android
* Implementação de notificações;
* Implementação do QrCode;

### Requisitos Avançados Web
* PieChart;

## Lista de Tarefas Android
- [x] Login
- [x] Bottom Navigation
- [x] Home Fragment
- [x] Category Fragment
- [x] Income Fragment
- [x] Expenses Fragment
- [x] Account Fragment
- [x] Edits para despesas, rendimentos, limites, categorias
- [x] Deletes despesas, rendimentos, limites, categorias
- [x] Validação
- [x] Implementação de gráfico das despesas agrupadas por categoria

## Lista de Tarefas Web
- [x] Login
- [x] Inserir dados
- [x] Visualização
- [x] Updates no android
- [x] Deletes no android

## Links Importantes
* (https://developer.android.com/training/data-storage/room/)
* (https://stackoverflow.com/questions/61875405/fill-spinner-with-room-database)
* (https://stackoverflow.com/questions/2390102/how-to-set-selected-item-of-spinner-by-value-not-by-position)
* (https://square.github.io/retrofit/)
* (https://cms.ipbeja.pt/pluginfile.php/330708/mod_resource/content/1/2%20-%20App%20Architecture.pdf)
* (https://developer.android.com/topic/libraries/architecture/viewmodel?hl=pt-br)
* (https://developer.android.com/topic/libraries/architecture/livedata?hl=pt-br)
* (https://developer.android.com/guide/navigation/navigation-getting-started?hl=pt-br)
* (https://developer.android.com/guide/navigation/navigation-pass-data?hl=pt-br#Safe-args)
* (https://developer.android.com/topic/libraries/architecture/viewmodel?hl=pt-br)
* (https://developer.android.com/topic/libraries/architecture/livedata?hl=pt-br)
* (https://developer.android.com/guide/navigation/navigation-getting-started?hl=pt-br)
* (https://www.mindrot.org/projects/jBCrypt/)
* (https://www.tutsmake.com/laravel-9-google-pie-chart-tutorial-example/?utm_content=cmp-true)
* (https://www.nicesnippets.com/blog/laravel-9-auth-scaffolding-using-bootstrap-laravel-ui)
* (https://blog.devgenius.io/laravel-tutorial-3-views-and-blade-templates-dd432571512b)
* (https://larainfo.com/blogs/laravel-9-rest-api-crud-tutorial-example)
* (https://www.positronx.io/php-laravel-crud-operations-mysql-tutorial/)
* (https://themewagon.com/themes/corona-free-responsive-bootstrap-4-admin-dashboard-template/)

## Links Importantes- Youtube
* (https://www.youtube.com/watch?v=_DFF3OloS-4)
* (https://www.youtube.com/watch?v=_DFF3OloS-4&t=1s)
* (https://www.youtube.com/playlist?list=PLDc9bt_00KcKijBHJTbVsYagHeJC6X3YD)

## End Points
* Todos Movimentos
(http://localhost:8000/api/movements)

* Todas Categorias
(http://localhost:8000/api/categories)

* Todos Users
(http://localhost:8000/api/users)

* Todos Movimentos por user
(http://localhost:8000/api/movements/{id})

* Todas Categorias por user
(http://localhost:8000/api/categories/{id})

* Soma todas as Receitas por user
(http://localhost:8000/api/movements/positive-sum/{id})

* Soma todas as despesas por user
(http://localhost:8000/api/movements/negative-sum/{id})


## Aplicações e Tecnologias
* Android Studio 
* Room
* Retrofit
* PieChart
* JbCrypt
* Html
* Css
* Javascript
* Laravel
* Xampp
* Cmd

