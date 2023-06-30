const { createApp } = Vue

const app = createApp({
  data() {
  return {
    cardType:"",
    cardColor:"",
      };
  },

  created(){
    AOS.init()
  },

  methods:{

 

    cardCreated(){
      Swal.fire({
        icon: 'success',
        title: 'Cool...',
        text: 'New card created',
        footer:'<a href="../htmlPages/Cards.html">Click here to see it</a>',        
        background:'#0e0747',
        color:'#ffffff'
      })
    },

    emptyField(){
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Please fill all the fields',       
        background:'#0e0747',
        color:'#ffffff'
      })
    },

    repeatedCard(){
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'You already own that kind of card',
        background:'#0e0747',
        color:'#ffffff'
      })
    },

    createCards(){
      axios.post('/api/clients/current/cards', `cardType=${this.cardType}&cardColor=${this.cardColor}`)
      .then(response => {
       
        console.log(response)
        if(response){
          this.cardCreated()
         
        }
       
          
        
      }).catch(err => {

        console.log(err)

        if(err.response.status === 400){
          this.emptyField()
        }

        if(err.response.status === 403){
          this.repeatedCard()
        }
        
      })
    }
  }

})
  .mount('#app')
  