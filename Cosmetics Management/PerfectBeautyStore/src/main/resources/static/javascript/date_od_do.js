function pressDate() {
      var do_input = new Date(document.getElementById('date-to').value);
      var od_input = new Date(document.getElementById('date-from').value);

      if ( do_input < od_input) {
        console.log(do_input<od_input);
          document.getElementById('date-to').style.boxShadow="0 0 3px red";
          document.getElementById('date-from').style.boxShadow="0 0 3px red";
          document.getElementById('date-from-to').innerHTML = 'Data od nie może być późniejsza za datę do';
          document.getElementById('date-valid').style.paddingBottom='1rem';
      } else {
        console.log(do_input<od_input);
          document.getElementById('date-to').style.boxShadow="0 0 0px red";
          document.getElementById('date-from').style.boxShadow="0 0 0px red";
          document.getElementById('date-from-to').innerHTML = '';
          document.getElementById('date-valid').style.paddingBottom='0rem';
      }

}
