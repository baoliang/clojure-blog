require 'date'
def test
  p DateTime.now()
  system "lein -v"
  p DateTime.now()
end
test()
