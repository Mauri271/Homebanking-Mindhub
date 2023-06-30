const { createApp } = Vue

createApp({
  data() {
  return {
        clients: {},
        accounts:{},
        loans:{}
      };
  },

  created(){
    this.loadData()

  },

  methods:{

  loadData(){
          axios.get("http://localhost:8080/api/clients/current")
          .then(res => {            
            
          this.clients= res.data         
          this.accounts = this.clients.accounts.sort((a,b) => a.balance - b.balance);
          this.loans = this.clients.loans

          })
          .catch(err=> console.log(err))
  },

  createAccount(){
    axios.post("http://localhost:8080/api/clients/current/accounts")
    .then(response => {
      if( response.status == 201){
        this.loadData()
        console.log(response.status)
      } 
    }).catch(error => {
      console.log(error)
      if (error){
        alert(`Accounts limit reached`)
      }
    } )


    
  }


  }



}).mount('#app')

const logout = createApp({
  data() {
  return {
        
      };
  },

  created(){
   
  },

  methods:{

  logOut(){
    axios.post('/api/logout').
    then(response => window.location.href="../htmlPages/index.html")
  },

  

  }

}).mount('#logout')