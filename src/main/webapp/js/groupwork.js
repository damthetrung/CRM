$(document).ready(function(){
      $(".btn-delete-job").click(function(){
          var id=$(this).attr("jobid");
          var This=$(this)
          $.ajax({
            method: "POST",
            url: "http://localhost:8080/demoservlet/groupwork?id="+id,
            //data: { name: "John", location: "Boston" }
          }).done(function( result ) {
              This.closest("tr").remove();
            });
      });

})