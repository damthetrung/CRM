$(document).ready(function(){
      $(".btn-delete-user").click(function(){

          var id=$(this).attr("userid");
          var This=$(this);
          $.ajax({
            method: "GET",
            url: "http://localhost:8080/demoservlet/user-delete?id="+id,
            //data: { id: id}
          }).done(function( result ) {
              This.closest("tr").remove();
            });
      });

})