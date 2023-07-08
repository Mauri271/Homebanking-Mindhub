const { createApp } = Vue

const app = createApp({
  data() {
  return {
        cards: {},
      };
  },

  created(){
    this.loadData()
  },

  methods:{

  loadData(){
          axios.get("/api/clients/current")
          .then(res => {            
            
          this.cards= res.data.cards.sort((a,b) => a.id - b.id)
          console.log(this.cards)
          

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