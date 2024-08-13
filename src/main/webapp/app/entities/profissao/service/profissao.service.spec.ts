import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IProfissao } from '../profissao.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../profissao.test-samples';

import { ProfissaoService } from './profissao.service';

const requireRestSample: IProfissao = {
  ...sampleWithRequiredData,
};

describe('Profissao Service', () => {
  let service: ProfissaoService;
  let httpMock: HttpTestingController;
  let expectedResult: IProfissao | IProfissao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ProfissaoService);
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

    it('should create a Profissao', () => {
      const profissao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(profissao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Profissao', () => {
      const profissao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(profissao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Profissao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Profissao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Profissao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProfissaoToCollectionIfMissing', () => {
      it('should add a Profissao to an empty array', () => {
        const profissao: IProfissao = sampleWithRequiredData;
        expectedResult = service.addProfissaoToCollectionIfMissing([], profissao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(profissao);
      });

      it('should not add a Profissao to an array that contains it', () => {
        const profissao: IProfissao = sampleWithRequiredData;
        const profissaoCollection: IProfissao[] = [
          {
            ...profissao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProfissaoToCollectionIfMissing(profissaoCollection, profissao);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Profissao to an array that doesn't contain it", () => {
        const profissao: IProfissao = sampleWithRequiredData;
        const profissaoCollection: IProfissao[] = [sampleWithPartialData];
        expectedResult = service.addProfissaoToCollectionIfMissing(profissaoCollection, profissao);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(profissao);
      });

      it('should add only unique Profissao to an array', () => {
        const profissaoArray: IProfissao[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const profissaoCollection: IProfissao[] = [sampleWithRequiredData];
        expectedResult = service.addProfissaoToCollectionIfMissing(profissaoCollection, ...profissaoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const profissao: IProfissao = sampleWithRequiredData;
        const profissao2: IProfissao = sampleWithPartialData;
        expectedResult = service.addProfissaoToCollectionIfMissing([], profissao, profissao2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(profissao);
        expect(expectedResult).toContain(profissao2);
      });

      it('should accept null and undefined values', () => {
        const profissao: IProfissao = sampleWithRequiredData;
        expectedResult = service.addProfissaoToCollectionIfMissing([], null, profissao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(profissao);
      });

      it('should return initial array if no Profissao is added', () => {
        const profissaoCollection: IProfissao[] = [sampleWithRequiredData];
        expectedResult = service.addProfissaoToCollectionIfMissing(profissaoCollection, undefined, null);
        expect(expectedResult).toEqual(profissaoCollection);
      });
    });

    describe('compareProfissao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProfissao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProfissao(entity1, entity2);
        const compareResult2 = service.compareProfissao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProfissao(entity1, entity2);
        const compareResult2 = service.compareProfissao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProfissao(entity1, entity2);
        const compareResult2 = service.compareProfissao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
