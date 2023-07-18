const { createApp } = Vue

const app = createApp({
  data() {
  return {
        cards: [],
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
          
 
          this.cards.forEach(card => {
            card.cardExpired = new Date(card.thruDate) < new Date();
          });
    
          console.log(this.cardExpirationDate);
            
    

          })
          .catch(err=> console.log(err))
  },

  deleteCard(id){
    console.log(id)
    axios.patch(`/api/clients/current/cards/${id}`)
    .then(res => {
      console.log(res)
      this.loadData()
    } )
    .catch(err => console.log(err))
    console.log(this.cards)

  }

  },





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