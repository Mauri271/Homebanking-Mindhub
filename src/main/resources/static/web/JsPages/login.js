const { createApp } = Vue

createApp({
  data() {
  return {
    email: "",
    password:"",
    firstName:"",
    lastName:"",
    emailRegistered:"",
    registeredPassword:""
      };
  },

  created(){
    
  },

  methods:{

    loginError(){
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Please fill all the fields',
      })
    },

    login(){
    
    axios.post(`/api/login?email=${this.email}&password=${this.password}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
    .then(response => {

        if(response.status === 200){
            window.location.href= "../htmlPages/accounts.html"
        }
    })
    .catch(error => {

   if(error){
    this.loginError()
   }
    })
  },

  passwordReset(){
    alert("An email has been sent to reset your password")
  },

  registerError(){
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: 'Please fill all the fields',
      background:'#0000'
    })
  },

  register(){

    console.log(this.firstName, this.lastName, this.registeredPassword,this.emailRegistered)
    axios.post('/api/clients ' , `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.emailRegistered}&password=${this.registeredPassword}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
    .then(response => {      

            axios.post('/api/login', `email=${this.emailRegistered}&password=${this.registeredPassword}`)
            .then(response => {
              window.location.href= "../htmlPages/index.html"
              console.log(response)
              
            })
            .catch(err => {
              console.log(err)
            
              
            })
            
      
    })
    .catch(error => {

      
    
     if(error){
      this.registerError();
     }

    })
  },

  
}

}).mount('#app')
  