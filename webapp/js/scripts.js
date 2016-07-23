// $(".qna-comment").on("click", ".answerWrite input[type=submit]", addAnswer);
$(".answerWrite input[type=submit]").click(addAnswer);
$("form[name=answerDelete]").on("submit", deleteAnswer);

function addAnswer(e) {
  e.preventDefault();

  var queryString = $("form[name=answer]").serialize();

  $.ajax({
    type : 'post',
    url : '/api/qna/addAnswer',
    data : queryString,
    dataType : 'json',
    error: onError,
    success : onSuccess,
  });
}

function onSuccess(json, status){
  var answer = json.answer;
  var answerTemplate = $("#answerTemplate").html();
  var template = answerTemplate.format(answer.writer, new Date(answer.createdDate), answer.contents, answer.answerId, answer.answerId);
  $(".qna-comment-slipp-articles").prepend(template);
  
  var countOfComment = Number($("#countOfComment").html());
  $("#countOfComment").html(countOfComment+1);
}

function onError(xhr, status) {
  alert("error");
}

function deleteAnswer(e) {
  e.preventDefault();
  var queryString = $(this).serialize();
  var answerId = this.answerId.value;
  $.ajax({
    type : 'post',
    url : '/api/qna/deleteAnswer',
    data : queryString,
    dataType : 'json',
    error: onError,
    success : function(json){
    	if(json.result.status){
    		$("#answer"+answerId).remove();
    	}
    },
  });
}

String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};