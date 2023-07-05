const {createApp} = Vue;

const app = createApp({
    data() {
    return {
          accounts:{},
          transferObject:{
            originAccount: "",
            destinationAccount:"",
            amount:"",
            description:""
          }
        };
    },
  
    created(){
     this.accountsData()
    },
  
    methods:{
  
    logOut(){
      axios.post('/api/logout').
      then(response => window.location.href="../htmlPages/index.html")
    },

    accountsData(){{
      axios.get("http://localhost:8080/api/clients/current")
      .then(res=> {
        
        this.accounts = res.data.accounts
        console.log(this.accounts)
      }).catch(err => console.log(err))
    }},

    transfer(){
      console.log(this.transferObject.originAccount, this.transferObject.destinationAccount, this.transferObject.amount, this.transferObject.description)

      axios.post('/api/transactions', this.transferObject)
      .then(res => console.log(res))
      .catch(err => console.log(err))
    }


  
    
  
    }
  
  }).mount('#app')