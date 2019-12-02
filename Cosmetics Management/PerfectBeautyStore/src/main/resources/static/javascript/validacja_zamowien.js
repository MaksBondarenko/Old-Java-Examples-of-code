function pressIlosc() {
      var id = this.name;
      if (parseFloat( this.value) <1) {
          this.style.boxShadow="0 0 3px red";
          document.getElementById(id).innerHTML = 'Ilość nie może być mniejsza od 1';
      } else {
        this.style.boxShadow="0 0 0px red";
        document.getElementById(id).innerHTML = '';
      }
}
function pressCheck() {
  var email_regex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  var word_regex = /^[A-Z]{1}[a-z]+$/;
  var phone_regex = /[+]{1}[0-9]{2}[\s]{1}[0-9]{3}-[0-9]{3}-[0-9]{3}/;
  var input = document.forms.input;
  if(!word_regex.test(input.imie.value)){
    document.forms.input.imie.style.boxShadow="0 0 3px red";
    document.getElementById('imie').innerHTML = 'Imię jest podane nieprawidlowo';
} else {
  document.forms.input.imie.style.boxShadow="0 0 0px red";
  document.getElementById('imie').innerHTML = '';
}
if(!word_regex.test(input.nazwisko.value)){
  document.forms.input.nazwisko.style.boxShadow="0 0 3px red";
  document.getElementById('nazwisko').innerHTML = 'Nazwisko jest podane nieprawidlowo';
} else {
  document.forms.input.nazwisko.style.boxShadow="0 0 0px red";
  document.getElementById('nazwisko').innerHTML = '';
}
if(!email_regex.test(input.email.value)){
  document.forms.input.email.style.boxShadow="0 0 3px red";
  document.getElementById('email').innerHTML = 'Email nie jest poprawny';
} else {
  document.forms.input.email.style.boxShadow="0 0 0px red";
  document.getElementById('email').innerHTML = '';
}
if(!phone_regex.test(input.phone.value)){
  document.forms.input.phone.style.boxShadow="0 0 3px red";
  document.getElementById('phone').innerHTML = 'Wprowadź telefon zgodnie z formatem: +00 000-000-000';
} else {
  document.forms.input.phone.style.boxShadow="0 0 0px red";
  document.getElementById('phone').innerHTML = '';
}
}
function pressString() {
      var id = this.name;
      if (this.value == "") {
          this.style.boxShadow="0 0 3px red";
          document.getElementById(id).innerHTML = 'Pole jest wymagane';
      } else {
        this.style.boxShadow="0 0 0px red";
        document.getElementById(id).innerHTML = '';
      }
}
function validate(){
  var email_regex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  var word_regex = /^[A-Z][a-z]+/;
  var phone_regex = /[+]{1}[0-9]{2}[ ]{1}[0-9]{3}-[0-9]{3}-[0-9]{3}/;
  var valid = true;
  var empty_values = new Set();
  var input = document.forms.input;
  for(var i = 0;i<7;i++){
    input[i].addEventListener("input",pressString);
    if(input[i].value==""){
      valid = false;
      document.getElementById(input[i].name).innerHTML = 'Pole jest wymagane';
      input[i].style.boxShadow="0 0 4px #CC0000";
    }
  }
  if(!word_regex.test(input.imie.value)){
    document.forms.input.imie.style.boxShadow="0 0 3px red";
    document.getElementById('imie').innerHTML = 'Imię jest podane nieprawidlowo';
    valid = false;
}
if(!word_regex.test(input.nazwisko.value)){
  document.forms.input.nazwisko.style.boxShadow="0 0 3px red";
  document.getElementById('nazwisko').innerHTML = 'Nazwisko jest podane nieprawidlowo';
  valid = false;
}
if(!email_regex.test(input.email.value)){
  document.forms.input.email.style.boxShadow="0 0 3px red";
  document.getElementById('email').innerHTML = 'Email nie jest poprawny';
  valid = false;
}
if(!phone_regex.test(input.phone.value)){
  document.forms.input.phone.style.boxShadow="0 0 3px red";
  document.getElementById('phone').innerHTML = 'Wprowadź telefon zgodnie z formatem: +00 000-000-000';
  valid = false;
}
if(!!document.getElementById('ilosc')){
    if(parseFloat( document.forms.input.ilosc1.value )<1){
      document.forms.input.ilosc1.style.boxShadow="0 0 3px red";
      document.getElementById('ilosc').innerHTML = 'Ilość nie może być mniejsza od 1';
      valid=false;
    }
    document.forms.input.ilosc.addEventListener('input',pressIlosc);
}
  document.forms["input"]["nazwisko"].addEventListener("input",pressCheck);
  document.forms["input"]["imie"].addEventListener("input",pressCheck);
  document.forms["input"]["email"].addEventListener("input",pressCheck);
  document.forms["input"]["phone"].addEventListener("input",pressCheck);
  if(valid) return confirm('Czy chesz zapisać?');
  else return valid;
}
