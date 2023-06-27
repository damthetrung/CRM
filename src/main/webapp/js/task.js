$(document).ready(function(){
      $(".btn-delete-task").click(function(){
          var id=$(this).attr("taskid");
          var This=$(this)
          $.ajax({
            method: "GET",
            url: "http://localhost:8080/demoservlet/task-delete?id="+id,
            //data: { name: "John", location: "Boston" }
          }).done(function( result ) {
              This.closest("tr").remove();
            });
      });

})