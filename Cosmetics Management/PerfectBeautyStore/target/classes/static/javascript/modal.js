function show() {
    document.getElementById('openModal').style.display='block';
}
function closeyslf() {
    document.getElementById('openModal').style.display='none';
}
// function clickRefresh(){
//     $( "#produkty" ).load(window.location.href + " #produkty" );
//     $( "#mlin" ).load(window.location.href + " #mlin" );
//     console.log('pzdc');
// }
function wydziel(element){
    var inp = document.getElementsByClassName("small2");
    var checkedval = document.getElementsByClassName("small");
    var wartosci = document.getElementsByClassName("wart");
    var prices = document.getElementsByClassName("prices");
    var ind;
    for(i=0;i<checkedval.length;i++){
        if(checkedval[i]==element)
            ind = i;
    }
    var row = element.parentElement.parentElement;
    if(element.checked){
        inp[ind].removeAttribute("disabled");
        inp[ind].value=1;
        row.style.backgroundColor="#EFCCE9";
        inp[ind].addEventListener("keyup",pressWart);
    }
    else {
        row.style.backgroundColor = "white";
        inp[ind].value=0;
        inp[ind].setAttribute("disabled","disabled");
    }
    var wartosc=0;
    for(i=0;i<inp.length;i++){
        wartosc+=parseFloat(inp[i].value)*parseFloat(prices[i].textContent);
    }
    wartosci[ind].innerHTML = parseFloat(prices[ind].textContent)*parseFloat(inp[ind].value);
    document.getElementById("wartosccala").innerHTML= 'Wartość zamówienia: '+wartosc+' (zł)';
}
function pressWart(){
    var inp = document.getElementsByClassName("small2");
    var wartosci = document.getElementsByClassName("wart");
    var prices = document.getElementsByClassName("prices");
    var count = document.getElementsByClassName("maxCount");
    var ind;
    if(parseFloat(this.value)<1) {
        this.value = 1;
    }
    for(i=0;i<inp.length;i++){
        if(inp[i]==this)
            ind = i;
    }
    if(parseFloat(this.value)>parseFloat(count[ind].textContent.substring(3,count[ind].textContent.length)))
        this.value=1;
    console.log(parseFloat(count[ind].textContent));
    if(this.value==""){
        wartosci[ind].innerHTML=0;
    } else {
        var wartosc = 0;
        for (i = 0; i < inp.length; i++) {
            wartosc += parseFloat(inp[i].value) * parseFloat(prices[i].textContent);
        }

        wartosci[ind].innerHTML = parseFloat(prices[ind].textContent) * parseFloat(this.value);
        document.getElementById("wartosccala").innerHTML = 'Wartość zamówienia: ' + wartosc + ' (zł)';
    }
}
function sprawdz(){
    var inp = document.getElementsByClassName("small2");
    var checkedval = document.getElementsByClassName("small");
    var wartosci = document.getElementsByClassName("wart");
    var prices = document.getElementsByClassName("prices");
    var row =document.getElementsByClassName("rowyow");
    for(i=0;i<inp.length;i++){
        if(inp[i].value==""){
            row[i].style.backgroundColor = "white";
            inp[i].value = 0;
            inp[i].setAttribute("disabled","disabled");
            checkedval[i].checked=false;
            wartosci[i].innerHTML=0;
            }
    }
    var wartosc=0;
    for(i=0;i<inp.length;i++){
        wartosc+=parseFloat(inp[i].value)*parseFloat(prices[i].textContent);
    }
    document.getElementById("wartosccala").innerHTML= 'Wartość zamówienia: '+wartosc+' (zł)';
}
function onBodyCreate(){
    var inp = document.getElementsByClassName("small2");
    var wartosci = document.getElementsByClassName("wart");
    var prices = document.getElementsByClassName("prices");
    var wartosc = 0;
    for (i = 0; i < inp.length; i++) {
        wartosc += parseFloat(inp[i].value) * parseFloat(prices[i].textContent);
        wartosci[i].innerHTML = parseFloat(prices[i].textContent) * parseFloat(inp[i].value);
    }
        document.getElementById("wartosccala").innerHTML = 'Wartość zamówienia: ' + wartosc + ' (zł)';
}