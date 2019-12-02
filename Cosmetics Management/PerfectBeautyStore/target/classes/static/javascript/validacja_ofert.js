function pressCena() {
      var id = this.name;
      console.log(typeof this.value);
      if(typeof this.value == 'string')
        document.getElementById(id).innerHTML = 'Powinna być podana liczba';
      if (parseFloat(this.value) < 0) {
          this.style.boxShadow="0 0 3px red";
          document.getElementById(id).innerHTML = 'Wartość tej liczby nie może być ujemna';
      } else {
        this.style.boxShadow="0 0 0px red";
        document.getElementById(id).innerHTML = '';
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
  var valid = true;
  var empty_values = new Set();
  var input = document.forms.input;
  for(var i = 0;i<input.length-3;i++){
    input[i].addEventListener("keyup",pressString);
    if(input[i].value==""){
      valid = false;
      document.getElementById(input[i].name).innerHTML = 'Pole jest wymagane';
      input[i].style.boxShadow="0 0 4px #CC0000";
    }
  }
  if(input.ilosc.value<0){
    document.forms.input.ilosc.style.boxShadow="0 0 3px red";
    document.getElementById('ilosc').innerHTML = 'Wartość tej liczby nie może być ujemna';
    valid = false;
}
  if(input.cena.value<0){
    document.forms.input.cena.style.boxShadow="0 0 3px red";
    document.getElementById('cena').innerHTML = 'Wartość tej liczby nie może być ujemna';
    valid = false;
}
  if(input.brutto.value<0){
    document.forms.input.brutto.style.boxShadow="0 0 3px red";
      document.getElementById('brutto').innerHTML = 'Wartość tej liczby nie może być ujemna';
      valid = false;
}
  document.forms["input"]["ilosc"].addEventListener("keyup",pressCena);
  document.forms["input"]["cena"].addEventListener("keyup",pressCena);
  document.forms["input"]["brutto"].addEventListener("keyup",pressCena);
  document.forms["input"]["ilosc"].addEventListener("wheel",pressCena);
  document.forms["input"]["cena"].addEventListener("wheel",pressCena);
  document.forms["input"]["brutto"].addEventListener("wheel",pressCena);
  if(valid) return confirm('Czy chesz zapisać?');
  else return valid;
}
