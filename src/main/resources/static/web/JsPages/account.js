const { createApp } = Vue

createApp({
  data() {
  return {
        transactions:[],
        account: [],
        params: "",
      };
  },

  created(){
    this.loadData()
  },

  methods:{

  loadData(){
          this.params = new URLSearchParams(location.search).get("id")
          axios.get(`http://localhost:8080/api/accounts/${this.params}`)
          .then(res => {          
            
          
          this.account = res.data
          console.log(this.account)
          this.transactions = this.account.transaction.sort((a,b) => b.date - a.date);
          console.log(this.transactions)

          })
          .catch(err=> console.log(err))
  },

    closeModal(){
      
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
  }
  }


}).mount('#logout')