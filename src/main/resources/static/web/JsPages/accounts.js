const { createApp } = Vue

createApp({
  data() {
  return {
        clients: {},
        accounts:[],
        loans:{},
      };
  },

  created(){
    this.loadData()

  },

  methods:{

  loadData(){
          axios.get("/api/clients/current")
          .then(res => {            
            
          this.clients= res.data         
          this.accounts = this.clients.accounts.sort((a,b) => a.id - b.id);
          this.loans = this.clients.loans.sort((a,b) => b.id - a.id);          
    console.log(this.accounts)

          })
          .catch(err=> console.log(err))
  },



  openModal() {
    document.getElementById('confirm').style.display = 'block';
  },

 closeModal() {
    document.getElementById('confirm').style.display = 'none';
  },

  createAccount(accountType) {  
        axios.post(`/api/clients/current/accounts/${accountType}`)
        .then(response => {
          if( response.status == 201){
            this.loadData()
            console.log(response.status)
          } 
        }).catch(error => {
          console.log(error)

        } )    
      
    console.log(`Crear cuenta de tipo: ${accountType}`);
  },
//   confirmAccount() {
//     document.getElementById('confirm').style.display = 'block';

//     if(this.confirmAccount()){
//       createAccount();{
//         axios.post(`/api/clients/current/accounts${accountType}`)
//         .then(response => {
//           if( response.status == 201){
//             this.loadData()
//             console.log(response.status)
//           } 
//         }).catch(error => {
//           console.log(error)
//           if (error){
//             alert(`Accounts limit reached`)
//           }
//         } )    
//       }
//     }


//   },

// closeModal() {
//     document.getElementById('confirm').style.display = 'none';
//   },



  deleteAccount(id){
    console.log(id)
    axios.patch(`/api/clients/current/accounts/${id}`)
    .then(res => {
      console.log(res);
      this.loadData()
      
    })
    .catch(err => console.log(err))
    console.log(this.accounts)
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