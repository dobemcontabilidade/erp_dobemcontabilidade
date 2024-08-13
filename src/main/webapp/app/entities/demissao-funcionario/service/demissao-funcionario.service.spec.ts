import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDemissaoFuncionario } from '../demissao-funcionario.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../demissao-funcionario.test-samples';

import { DemissaoFuncionarioService } from './demissao-funcionario.service';

const requireRestSample: IDemissaoFuncionario = {
  ...sampleWithRequiredData,
};

describe('DemissaoFuncionario Service', () => {
  let service: DemissaoFuncionarioService;
  let httpMock: HttpTestingController;
  let expectedResult: IDemissaoFuncionario | IDemissaoFuncionario[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DemissaoFuncionarioService);
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

    it('should create a DemissaoFuncionario', () => {
      const demissaoFuncionario = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(demissaoFuncionario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DemissaoFuncionario', () => {
      const demissaoFuncionario = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(demissaoFuncionario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DemissaoFuncionario', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DemissaoFuncionario', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DemissaoFuncionario', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDemissaoFuncionarioToCollectionIfMissing', () => {
      it('should add a DemissaoFuncionario to an empty array', () => {
        const demissaoFuncionario: IDemissaoFuncionario = sampleWithRequiredData;
        expectedResult = service.addDemissaoFuncionarioToCollectionIfMissing([], demissaoFuncionario);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demissaoFuncionario);
      });

      it('should not add a DemissaoFuncionario to an array that contains it', () => {
        const demissaoFuncionario: IDemissaoFuncionario = sampleWithRequiredData;
        const demissaoFuncionarioCollection: IDemissaoFuncionario[] = [
          {
            ...demissaoFuncionario,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDemissaoFuncionarioToCollectionIfMissing(demissaoFuncionarioCollection, demissaoFuncionario);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DemissaoFuncionario to an array that doesn't contain it", () => {
        const demissaoFuncionario: IDemissaoFuncionario = sampleWithRequiredData;
        const demissaoFuncionarioCollection: IDemissaoFuncionario[] = [sampleWithPartialData];
        expectedResult = service.addDemissaoFuncionarioToCollectionIfMissing(demissaoFuncionarioCollection, demissaoFuncionario);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demissaoFuncionario);
      });

      it('should add only unique DemissaoFuncionario to an array', () => {
        const demissaoFuncionarioArray: IDemissaoFuncionario[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const demissaoFuncionarioCollection: IDemissaoFuncionario[] = [sampleWithRequiredData];
        expectedResult = service.addDemissaoFuncionarioToCollectionIfMissing(demissaoFuncionarioCollection, ...demissaoFuncionarioArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const demissaoFuncionario: IDemissaoFuncionario = sampleWithRequiredData;
        const demissaoFuncionario2: IDemissaoFuncionario = sampleWithPartialData;
        expectedResult = service.addDemissaoFuncionarioToCollectionIfMissing([], demissaoFuncionario, demissaoFuncionario2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demissaoFuncionario);
        expect(expectedResult).toContain(demissaoFuncionario2);
      });

      it('should accept null and undefined values', () => {
        const demissaoFuncionario: IDemissaoFuncionario = sampleWithRequiredData;
        expectedResult = service.addDemissaoFuncionarioToCollectionIfMissing([], null, demissaoFuncionario, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demissaoFuncionario);
      });

      it('should return initial array if no DemissaoFuncionario is added', () => {
        const demissaoFuncionarioCollection: IDemissaoFuncionario[] = [sampleWithRequiredData];
        expectedResult = service.addDemissaoFuncionarioToCollectionIfMissing(demissaoFuncionarioCollection, undefined, null);
        expect(expectedResult).toEqual(demissaoFuncionarioCollection);
      });
    });

    describe('compareDemissaoFuncionario', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDemissaoFuncionario(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDemissaoFuncionario(entity1, entity2);
        const compareResult2 = service.compareDemissaoFuncionario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDemissaoFuncionario(entity1, entity2);
        const compareResult2 = service.compareDemissaoFuncionario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDemissaoFuncionario(entity1, entity2);
        const compareResult2 = service.compareDemissaoFuncionario(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
