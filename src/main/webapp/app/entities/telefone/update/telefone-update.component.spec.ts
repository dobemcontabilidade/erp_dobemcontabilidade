import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPessoaFisica } from 'app/entities/pessoa-fisica/pessoa-fisica.model';
import { PessoaFisicaService } from 'app/entities/pessoa-fisica/service/pessoa-fisica.service';
import { TelefoneService } from '../service/telefone.service';
import { ITelefone } from '../telefone.model';
import { TelefoneFormService } from './telefone-form.service';

import { TelefoneUpdateComponent } from './telefone-update.component';

describe('Telefone Management Update Component', () => {
  let comp: TelefoneUpdateComponent;
  let fixture: ComponentFixture<TelefoneUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let telefoneFormService: TelefoneFormService;
  let telefoneService: TelefoneService;
  let pessoaFisicaService: PessoaFisicaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TelefoneUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(TelefoneUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TelefoneUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    telefoneFormService = TestBed.inject(TelefoneFormService);
    telefoneService = TestBed.inject(TelefoneService);
    pessoaFisicaService = TestBed.inject(PessoaFisicaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PessoaFisica query and add missing value', () => {
      const telefone: ITelefone = { id: 456 };
      const pessoa: IPessoaFisica = { id: 30206 };
      telefone.pessoa = pessoa;

      const pessoaFisicaCollection: IPessoaFisica[] = [{ id: 9820 }];
      jest.spyOn(pessoaFisicaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoaFisicaCollection })));
      const additionalPessoaFisicas = [pessoa];
      const expectedCollection: IPessoaFisica[] = [...additionalPessoaFisicas, ...pessoaFisicaCollection];
      jest.spyOn(pessoaFisicaService, 'addPessoaFisicaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ telefone });
      comp.ngOnInit();

      expect(pessoaFisicaService.query).toHaveBeenCalled();
      expect(pessoaFisicaService.addPessoaFisicaToCollectionIfMissing).toHaveBeenCalledWith(
        pessoaFisicaCollection,
        ...additionalPessoaFisicas.map(expect.objectContaining),
      );
      expect(comp.pessoaFisicasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const telefone: ITelefone = { id: 456 };
      const pessoa: IPessoaFisica = { id: 8016 };
      telefone.pessoa = pessoa;

      activatedRoute.data = of({ telefone });
      comp.ngOnInit();

      expect(comp.pessoaFisicasSharedCollection).toContain(pessoa);
      expect(comp.telefone).toEqual(telefone);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITelefone>>();
      const telefone = { id: 123 };
      jest.spyOn(telefoneFormService, 'getTelefone').mockReturnValue(telefone);
      jest.spyOn(telefoneService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ telefone });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: telefone }));
      saveSubject.complete();

      // THEN
      expect(telefoneFormService.getTelefone).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(telefoneService.update).toHaveBeenCalledWith(expect.objectContaining(telefone));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITelefone>>();
      const telefone = { id: 123 };
      jest.spyOn(telefoneFormService, 'getTelefone').mockReturnValue({ id: null });
      jest.spyOn(telefoneService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ telefone: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: telefone }));
      saveSubject.complete();

      // THEN
      expect(telefoneFormService.getTelefone).toHaveBeenCalled();
      expect(telefoneService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITelefone>>();
      const telefone = { id: 123 };
      jest.spyOn(telefoneService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ telefone });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(telefoneService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePessoaFisica', () => {
      it('Should forward to pessoaFisicaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pessoaFisicaService, 'comparePessoaFisica');
        comp.comparePessoaFisica(entity, entity2);
        expect(pessoaFisicaService.comparePessoaFisica).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
