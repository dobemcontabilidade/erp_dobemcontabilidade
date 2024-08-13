import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../criterio-avaliacao-ator.test-samples';

import { CriterioAvaliacaoAtorFormService } from './criterio-avaliacao-ator-form.service';

describe('CriterioAvaliacaoAtor Form Service', () => {
  let service: CriterioAvaliacaoAtorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CriterioAvaliacaoAtorFormService);
  });

  describe('Service methods', () => {
    describe('createCriterioAvaliacaoAtorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCriterioAvaliacaoAtorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descricao: expect.any(Object),
            ativo: expect.any(Object),
            criterioAvaliacao: expect.any(Object),
            atorAvaliado: expect.any(Object),
          }),
        );
      });

      it('passing ICriterioAvaliacaoAtor should create a new form with FormGroup', () => {
        const formGroup = service.createCriterioAvaliacaoAtorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descricao: expect.any(Object),
            ativo: expect.any(Object),
            criterioAvaliacao: expect.any(Object),
            atorAvaliado: expect.any(Object),
          }),
        );
      });
    });

    describe('getCriterioAvaliacaoAtor', () => {
      it('should return NewCriterioAvaliacaoAtor for default CriterioAvaliacaoAtor initial value', () => {
        const formGroup = service.createCriterioAvaliacaoAtorFormGroup(sampleWithNewData);

        const criterioAvaliacaoAtor = service.getCriterioAvaliacaoAtor(formGroup) as any;

        expect(criterioAvaliacaoAtor).toMatchObject(sampleWithNewData);
      });

      it('should return NewCriterioAvaliacaoAtor for empty CriterioAvaliacaoAtor initial value', () => {
        const formGroup = service.createCriterioAvaliacaoAtorFormGroup();

        const criterioAvaliacaoAtor = service.getCriterioAvaliacaoAtor(formGroup) as any;

        expect(criterioAvaliacaoAtor).toMatchObject({});
      });

      it('should return ICriterioAvaliacaoAtor', () => {
        const formGroup = service.createCriterioAvaliacaoAtorFormGroup(sampleWithRequiredData);

        const criterioAvaliacaoAtor = service.getCriterioAvaliacaoAtor(formGroup) as any;

        expect(criterioAvaliacaoAtor).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICriterioAvaliacaoAtor should not enable id FormControl', () => {
        const formGroup = service.createCriterioAvaliacaoAtorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCriterioAvaliacaoAtor should disable id FormControl', () => {
        const formGroup = service.createCriterioAvaliacaoAtorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
