import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IContratoFuncionario } from '../contrato-funcionario.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../contrato-funcionario.test-samples';

import { ContratoFuncionarioService } from './contrato-funcionario.service';

const requireRestSample: IContratoFuncionario = {
  ...sampleWithRequiredData,
};

describe('ContratoFuncionario Service', () => {
  let service: ContratoFuncionarioService;
  let httpMock: HttpTestingController;
  let expectedResult: IContratoFuncionario | IContratoFuncionario[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ContratoFuncionarioService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ContratoFuncionario', () => {
      const contratoFuncionario = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(contratoFuncionario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ContratoFuncionario', () => {
      const contratoFuncionario = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(contratoFuncionario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ContratoFuncionario', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ContratoFuncionario', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ContratoFuncionario', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addContratoFuncionarioToCollectionIfMissing', () => {
      it('should add a ContratoFuncionario to an empty array', () => {
        const contratoFuncionario: IContratoFuncionario = sampleWithRequiredData;
        expectedResult = service.addContratoFuncionarioToCollectionIfMissing([], contratoFuncionario);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contratoFuncionario);
      });

      it('should not add a ContratoFuncionario to an array that contains it', () => {
        const contratoFuncionario: IContratoFuncionario = sampleWithRequiredData;
        const contratoFuncionarioCollection: IContratoFuncionario[] = [
          {
            ...contratoFuncionario,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addContratoFuncionarioToCollectionIfMissing(contratoFuncionarioCollection, contratoFuncionario);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ContratoFuncionario to an array that doesn't contain it", () => {
        const contratoFuncionario: IContratoFuncionario = sampleWithRequiredData;
        const contratoFuncionarioCollection: IContratoFuncionario[] = [sampleWithPartialData];
        expectedResult = service.addContratoFuncionarioToCollectionIfMissing(contratoFuncionarioCollection, contratoFuncionario);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contratoFuncionario);
      });

      it('should add only unique ContratoFuncionario to an array', () => {
        const contratoFuncionarioArray: IContratoFuncionario[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const contratoFuncionarioCollection: IContratoFuncionario[] = [sampleWithRequiredData];
        expectedResult = service.addContratoFuncionarioToCollectionIfMissing(contratoFuncionarioCollection, ...contratoFuncionarioArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const contratoFuncionario: IContratoFuncionario = sampleWithRequiredData;
        const contratoFuncionario2: IContratoFuncionario = sampleWithPartialData;
        expectedResult = service.addContratoFuncionarioToCollectionIfMissing([], contratoFuncionario, contratoFuncionario2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contratoFuncionario);
        expect(expectedResult).toContain(contratoFuncionario2);
      });

      it('should accept null and undefined values', () => {
        const contratoFuncionario: IContratoFuncionario = sampleWithRequiredData;
        expectedResult = service.addContratoFuncionarioToCollectionIfMissing([], null, contratoFuncionario, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contratoFuncionario);
      });

      it('should return initial array if no ContratoFuncionario is added', () => {
        const contratoFuncionarioCollection: IContratoFuncionario[] = [sampleWithRequiredData];
        expectedResult = service.addContratoFuncionarioToCollectionIfMissing(contratoFuncionarioCollection, undefined, null);
        expect(expectedResult).toEqual(contratoFuncionarioCollection);
      });
    });

    describe('compareContratoFuncionario', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareContratoFuncionario(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareContratoFuncionario(entity1, entity2);
        const compareResult2 = service.compareContratoFuncionario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareContratoFuncionario(entity1, entity2);
        const compareResult2 = service.compareContratoFuncionario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareContratoFuncionario(entity1, entity2);
        const compareResult2 = service.compareContratoFuncionario(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
