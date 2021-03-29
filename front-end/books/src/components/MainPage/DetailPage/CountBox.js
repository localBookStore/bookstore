import styled from "styled-components"

const CountBox = ({rest, bookCount, setBookCount}) => {

  const Upper = () => {
    setBookCount(bookCount === rest ? rest: bookCount+1)
  }
  const Lower = () => {
    setBookCount(bookCount === 1 ? 1 : bookCount-1)
  }
  const inputValue = (e) => {
    const value = e.target.value;
    if (isFinite(value) && value*1 < 1000){
      setBookCount(value*1)
    } else {
      setBookCount(bookCount)
    }
  }

  return <Counter>
    <Button onClick={Lower}>-</Button>
    <Number type="text" value={bookCount} onChange={e => inputValue(e)} />
    <Button onClick={Upper}>+</Button>
  </Counter>
}
export default CountBox;


const Counter = styled.div`
  position: absolute;
  bottom: 53px;
  left: 750px;
`
const Button = styled.button`
  border: 1 solid #4CAF50;
  background-color: white;
  border-radius: 5px;

  color: green;
  font-size:16px;
  font-weight: 500;
  &:hover {
    background-color: #4CAF50;
    color: white;
  }
`

const Number = styled.input`
  margin:0 10px;
  text-align:center;
  width:90px;
`