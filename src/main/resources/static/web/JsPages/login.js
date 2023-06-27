const { createApp } = Vue

createApp({
  data() {
  return {
    email: "",
    password:"",
    firstName:"",
    lastName:"",
      };
  },

  created(){
    
  },

  methods:{

    login(){
    
    axios.post(`/api/login?email=${this.email}&password=${this.password}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
    .then(response => {

        if(response.status === 200){
            window.location.href= "../htmlPages/accounts.html"
        }
    })
    .catch(error => {
        this.err = "Invalid Data"
        console.log(this.err)
        this.showNotification(this.err, 'error')
    })
  },

  passwordReset(){
    alert("An email has been sent to reset your password")
  },

  register(){
    axios.post(`/api/clients?firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
    .then(response => {

        if(response.status === 201 || response.status == 200){
            this.showNotification('Registered Client', 'success')
            window.location.href= "../htmlPages/index.html"
        } 
    })
    .catch(error => { console.log(error)
    
        this.err = error.response.data;
        console.log(this.err)
        this.showNotification(this.err, 'error');

    })
  },
  showNotification(message, type) {
    const toast = document.createElement('div');
    toast.classList.add('toastify', type);
    toast.textContent = message;
    document.body.appendChild(toast);

    setTimeout(() => {
      toast.classList.add('show');
      setTimeout(() => {
        toast.classList.remove('show');
        setTimeout(() => {
          document.body.removeChild(toast);
        }, 300);
      }, 2000);
    }, 100);
},
}

}).mount('#app')
  