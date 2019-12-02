function pressCena() {
      var do_input = document.getElementById('do');
      var od_input = document.getElementById('od');
      var dodatnie_od = true;
      var dodatnie_do = true;

      if (parseFloat(do_input.value) < 0) {
          do_input.style.boxShadow="0 0 3px red";
          document.getElementById('od-do').innerHTML = 'Wartość tej liczby nie może być ujemna';
          document.getElementById('cena-valid').style.paddingBottom='1rem';
          dodatnie_do = false;
      } else {
          do_input.style.boxShadow="0 0 0px red";
          document.getElementById('od-do').innerHTML = '';
          document.getElementById('cena-valid').style.paddingBottom='0rem';
          //dodatnie_do = true;
      }
      if (parseFloat(od_input.value) < 0) {
          od_input.style.boxShadow="0 0 3px red";
          document.getElementById('od-do').innerHTML = 'Wartość tej liczby nie może być ujemna';
          document.getElementById('cena-valid').style.paddingBottom='1rem';
          dodatnie_od = false;
      } else if(dodatnie_do) {
          od_input.style.boxShadow="0 0 0px red";
          document.getElementById('od-do').innerHTML = '';
          document.getElementById('cena-valid').style.paddingBottom='0rem';
          //dodatnie_od = true;
      } else{
        od_input.style.boxShadow="0 0 0px red";
        //dodatnie_od = true;
      }
      if ( dodatnie_od && dodatnie_do && ( parseFloat(do_input.value) < parseFloat(od_input.value))) {
          do_input.style.boxShadow="0 0 3px red";
          od_input.style.boxShadow="0 0 3px red";
          document.getElementById('od-do').innerHTML = 'Wartość od nie może być większa za wartość do';
          document.getElementById('cena-valid').style.paddingBottom='1rem';
      } else if(dodatnie_od && dodatnie_do) {
          do_input.style.boxShadow="0 0 0px red";
          od_input.style.boxShadow="0 0 0px red";
          document.getElementById('od-do').innerHTML = '';
          document.getElementById('cena-valid').style.paddingBottom='0rem';
      }
}
