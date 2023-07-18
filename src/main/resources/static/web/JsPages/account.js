const { createApp } = Vue

createApp({
  data() {
  return {
        transactions:[],
        account: [],
        params: "",
        hidden: null
      };
  },

  created(){
    this.loadData()
  },

  methods:{

  loadData(){
          this.params = new URLSearchParams(location.search).get("id")
          axios.get(`/api/accounts/${this.params}`)
          .then(res => {          
            
          
          this.account = res.data
          console.log(this.account)
          this.transactions = this.account.transaction.sort((a,b) => b.id - a.id);

          this.hidden = this.transactions.map(transaction => transaction.hidden).find(h => h )
       
          console.log(this.hidden)

          console.log(this.transactions)

          })
          .catch(err=> console.log(err))
  },

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