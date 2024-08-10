import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../profissao.test-samples';

import { ProfissaoFormService } from './profissao-form.service';

describe('Profissao Form Service', () => {
  let service: ProfissaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProfissaoFormService);
  });

  describe('Service methods', () => {
    describe('createProfissaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProfissaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            socio: expect.any(Object),
          }),
        );
      });

      it('passing IProfissao should create a new form with FormGroup', () => {
        const formGroup = service.createProfissaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            socio: expect.any(Object),
          }),
        );
      });
    });

    describe('getProfissao', () => {
      it('should return NewProfissao for default Profissao initial value', () => {
        const formGroup = service.createProfissaoFormGroup(sampleWithNewData);

        const profissao = service.getProfissao(formGroup) as any;

        expect(profissao).toMatchObject(sampleWithNewData);
      });

      it('should return NewProfissao for empty Profissao initial value', () => {
        const formGroup = service.createProfissaoFormGroup();

        const profissao = service.getProfissao(formGroup) as any;

        expect(profissao).toMatchObject({});
      });

      it('should return IProfissao', () => {
        const formGroup = service.createProfissaoFormGroup(sampleWithRequiredData);

        const profissao = service.getProfissao(formGroup) as any;

        expect(profissao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProfissao should not enable id FormControl', () => {
        const formGroup = service.createProfissaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProfissao should disable id FormControl', () => {
        const formGroup = service.createProfissaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
