import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../feed-back-usuario-para-contador.test-samples';

import { FeedBackUsuarioParaContadorFormService } from './feed-back-usuario-para-contador-form.service';

describe('FeedBackUsuarioParaContador Form Service', () => {
  let service: FeedBackUsuarioParaContadorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FeedBackUsuarioParaContadorFormService);
  });

  describe('Service methods', () => {
    describe('createFeedBackUsuarioParaContadorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFeedBackUsuarioParaContadorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            comentario: expect.any(Object),
            pontuacao: expect.any(Object),
            usuarioEmpresa: expect.any(Object),
            usuarioContador: expect.any(Object),
            criterioAvaliacaoAtor: expect.any(Object),
            ordemServico: expect.any(Object),
          }),
        );
      });

      it('passing IFeedBackUsuarioParaContador should create a new form with FormGroup', () => {
        const formGroup = service.createFeedBackUsuarioParaContadorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            comentario: expect.any(Object),
            pontuacao: expect.any(Object),
            usuarioEmpresa: expect.any(Object),
            usuarioContador: expect.any(Object),
            criterioAvaliacaoAtor: expect.any(Object),
            ordemServico: expect.any(Object),
          }),
        );
      });
    });

    describe('getFeedBackUsuarioParaContador', () => {
      it('should return NewFeedBackUsuarioParaContador for default FeedBackUsuarioParaContador initial value', () => {
        const formGroup = service.createFeedBackUsuarioParaContadorFormGroup(sampleWithNewData);

        const feedBackUsuarioParaContador = service.getFeedBackUsuarioParaContador(formGroup) as any;

        expect(feedBackUsuarioParaContador).toMatchObject(sampleWithNewData);
      });

      it('should return NewFeedBackUsuarioParaContador for empty FeedBackUsuarioParaContador initial value', () => {
        const formGroup = service.createFeedBackUsuarioParaContadorFormGroup();

        const feedBackUsuarioParaContador = service.getFeedBackUsuarioParaContador(formGroup) as any;

        expect(feedBackUsuarioParaContador).toMatchObject({});
      });

      it('should return IFeedBackUsuarioParaContador', () => {
        const formGroup = service.createFeedBackUsuarioParaContadorFormGroup(sampleWithRequiredData);

        const feedBackUsuarioParaContador = service.getFeedBackUsuarioParaContador(formGroup) as any;

        expect(feedBackUsuarioParaContador).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFeedBackUsuarioParaContador should not enable id FormControl', () => {
        const formGroup = service.createFeedBackUsuarioParaContadorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFeedBackUsuarioParaContador should disable id FormControl', () => {
        const formGroup = service.createFeedBackUsuarioParaContadorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
