import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../subclasse-cnae.test-samples';

import { SubclasseCnaeFormService } from './subclasse-cnae-form.service';

describe('SubclasseCnae Form Service', () => {
  let service: SubclasseCnaeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubclasseCnaeFormService);
  });

  describe('Service methods', () => {
    describe('createSubclasseCnaeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSubclasseCnaeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            descricao: expect.any(Object),
            anexo: expect.any(Object),
            atendidoFreemium: expect.any(Object),
            atendido: expect.any(Object),
            optanteSimples: expect.any(Object),
            aceitaMEI: expect.any(Object),
            categoria: expect.any(Object),
            classe: expect.any(Object),
            segmentoCnaes: expect.any(Object),
          }),
        );
      });

      it('passing ISubclasseCnae should create a new form with FormGroup', () => {
        const formGroup = service.createSubclasseCnaeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            descricao: expect.any(Object),
            anexo: expect.any(Object),
            atendidoFreemium: expect.any(Object),
            atendido: expect.any(Object),
            optanteSimples: expect.any(Object),
            aceitaMEI: expect.any(Object),
            categoria: expect.any(Object),
            classe: expect.any(Object),
            segmentoCnaes: expect.any(Object),
          }),
        );
      });
    });

    describe('getSubclasseCnae', () => {
      it('should return NewSubclasseCnae for default SubclasseCnae initial value', () => {
        const formGroup = service.createSubclasseCnaeFormGroup(sampleWithNewData);

        const subclasseCnae = service.getSubclasseCnae(formGroup) as any;

        expect(subclasseCnae).toMatchObject(sampleWithNewData);
      });

      it('should return NewSubclasseCnae for empty SubclasseCnae initial value', () => {
        const formGroup = service.createSubclasseCnaeFormGroup();

        const subclasseCnae = service.getSubclasseCnae(formGroup) as any;

        expect(subclasseCnae).toMatchObject({});
      });

      it('should return ISubclasseCnae', () => {
        const formGroup = service.createSubclasseCnaeFormGroup(sampleWithRequiredData);

        const subclasseCnae = service.getSubclasseCnae(formGroup) as any;

        expect(subclasseCnae).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISubclasseCnae should not enable id FormControl', () => {
        const formGroup = service.createSubclasseCnaeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSubclasseCnae should disable id FormControl', () => {
        const formGroup = service.createSubclasseCnaeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
