import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEscolaridade } from '../escolaridade.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../escolaridade.test-samples';

import { EscolaridadeService } from './escolaridade.service';

const requireRestSample: IEscolaridade = {
  ...sampleWithRequiredData,
};

describe('Escolaridade Service', () => {
  let service: EscolaridadeService;
  let httpMock: HttpTestingController;
  let expectedResult: IEscolaridade | IEscolaridade[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EscolaridadeService);
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

    it('should create a Escolaridade', () => {
      const escolaridade = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(escolaridade).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Escolaridade', () => {
      const escolaridade = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(escolaridade).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Escolaridade', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Escolaridade', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Escolaridade', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEscolaridadeToCollectionIfMissing', () => {
      it('should add a Escolaridade to an empty array', () => {
        const escolaridade: IEscolaridade = sampleWithRequiredData;
        expectedResult = service.addEscolaridadeToCollectionIfMissing([], escolaridade);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(escolaridade);
      });

      it('should not add a Escolaridade to an array that contains it', () => {
        const escolaridade: IEscolaridade = sampleWithRequiredData;
        const escolaridadeCollection: IEscolaridade[] = [
          {
            ...escolaridade,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEscolaridadeToCollectionIfMissing(escolaridadeCollection, escolaridade);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Escolaridade to an array that doesn't contain it", () => {
        const escolaridade: IEscolaridade = sampleWithRequiredData;
        const escolaridadeCollection: IEscolaridade[] = [sampleWithPartialData];
        expectedResult = service.addEscolaridadeToCollectionIfMissing(escolaridadeCollection, escolaridade);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(escolaridade);
      });

      it('should add only unique Escolaridade to an array', () => {
        const escolaridadeArray: IEscolaridade[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const escolaridadeCollection: IEscolaridade[] = [sampleWithRequiredData];
        expectedResult = service.addEscolaridadeToCollectionIfMissing(escolaridadeCollection, ...escolaridadeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const escolaridade: IEscolaridade = sampleWithRequiredData;
        const escolaridade2: IEscolaridade = sampleWithPartialData;
        expectedResult = service.addEscolaridadeToCollectionIfMissing([], escolaridade, escolaridade2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(escolaridade);
        expect(expectedResult).toContain(escolaridade2);
      });

      it('should accept null and undefined values', () => {
        const escolaridade: IEscolaridade = sampleWithRequiredData;
        expectedResult = service.addEscolaridadeToCollectionIfMissing([], null, escolaridade, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(escolaridade);
      });

      it('should return initial array if no Escolaridade is added', () => {
        const escolaridadeCollection: IEscolaridade[] = [sampleWithRequiredData];
        expectedResult = service.addEscolaridadeToCollectionIfMissing(escolaridadeCollection, undefined, null);
        expect(expectedResult).toEqual(escolaridadeCollection);
      });
    });

    describe('compareEscolaridade', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEscolaridade(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEscolaridade(entity1, entity2);
        const compareResult2 = service.compareEscolaridade(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEscolaridade(entity1, entity2);
        const compareResult2 = service.compareEscolaridade(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEscolaridade(entity1, entity2);
        const compareResult2 = service.compareEscolaridade(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
