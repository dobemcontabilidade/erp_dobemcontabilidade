import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEmpresaModelo } from '../empresa-modelo.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../empresa-modelo.test-samples';

import { EmpresaModeloService } from './empresa-modelo.service';

const requireRestSample: IEmpresaModelo = {
  ...sampleWithRequiredData,
};

describe('EmpresaModelo Service', () => {
  let service: EmpresaModeloService;
  let httpMock: HttpTestingController;
  let expectedResult: IEmpresaModelo | IEmpresaModelo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EmpresaModeloService);
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

    it('should create a EmpresaModelo', () => {
      const empresaModelo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(empresaModelo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EmpresaModelo', () => {
      const empresaModelo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(empresaModelo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EmpresaModelo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EmpresaModelo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EmpresaModelo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEmpresaModeloToCollectionIfMissing', () => {
      it('should add a EmpresaModelo to an empty array', () => {
        const empresaModelo: IEmpresaModelo = sampleWithRequiredData;
        expectedResult = service.addEmpresaModeloToCollectionIfMissing([], empresaModelo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(empresaModelo);
      });

      it('should not add a EmpresaModelo to an array that contains it', () => {
        const empresaModelo: IEmpresaModelo = sampleWithRequiredData;
        const empresaModeloCollection: IEmpresaModelo[] = [
          {
            ...empresaModelo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEmpresaModeloToCollectionIfMissing(empresaModeloCollection, empresaModelo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EmpresaModelo to an array that doesn't contain it", () => {
        const empresaModelo: IEmpresaModelo = sampleWithRequiredData;
        const empresaModeloCollection: IEmpresaModelo[] = [sampleWithPartialData];
        expectedResult = service.addEmpresaModeloToCollectionIfMissing(empresaModeloCollection, empresaModelo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(empresaModelo);
      });

      it('should add only unique EmpresaModelo to an array', () => {
        const empresaModeloArray: IEmpresaModelo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const empresaModeloCollection: IEmpresaModelo[] = [sampleWithRequiredData];
        expectedResult = service.addEmpresaModeloToCollectionIfMissing(empresaModeloCollection, ...empresaModeloArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const empresaModelo: IEmpresaModelo = sampleWithRequiredData;
        const empresaModelo2: IEmpresaModelo = sampleWithPartialData;
        expectedResult = service.addEmpresaModeloToCollectionIfMissing([], empresaModelo, empresaModelo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(empresaModelo);
        expect(expectedResult).toContain(empresaModelo2);
      });

      it('should accept null and undefined values', () => {
        const empresaModelo: IEmpresaModelo = sampleWithRequiredData;
        expectedResult = service.addEmpresaModeloToCollectionIfMissing([], null, empresaModelo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(empresaModelo);
      });

      it('should return initial array if no EmpresaModelo is added', () => {
        const empresaModeloCollection: IEmpresaModelo[] = [sampleWithRequiredData];
        expectedResult = service.addEmpresaModeloToCollectionIfMissing(empresaModeloCollection, undefined, null);
        expect(expectedResult).toEqual(empresaModeloCollection);
      });
    });

    describe('compareEmpresaModelo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEmpresaModelo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEmpresaModelo(entity1, entity2);
        const compareResult2 = service.compareEmpresaModelo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEmpresaModelo(entity1, entity2);
        const compareResult2 = service.compareEmpresaModelo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEmpresaModelo(entity1, entity2);
        const compareResult2 = service.compareEmpresaModelo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
