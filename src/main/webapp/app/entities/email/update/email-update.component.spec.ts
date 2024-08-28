import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPessoaFisica } from 'app/entities/pessoa-fisica/pessoa-fisica.model';
import { PessoaFisicaService } from 'app/entities/pessoa-fisica/service/pessoa-fisica.service';
import { EmailService } from '../service/email.service';
import { IEmail } from '../email.model';
import { EmailFormService } from './email-form.service';

import { EmailUpdateComponent } from './email-update.component';

describe('Email Management Update Component', () => {
  let comp: EmailUpdateComponent;
  let fixture: ComponentFixture<EmailUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let emailFormService: EmailFormService;
  let emailService: EmailService;
  let pessoaFisicaService: PessoaFisicaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EmailUpdateComponent],
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
      .overrideTemplate(EmailUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmailUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    emailFormService = TestBed.inject(EmailFormService);
    emailService = TestBed.inject(EmailService);
    pessoaFisicaService = TestBed.inject(PessoaFisicaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PessoaFisica query and add missing value', () => {
      const email: IEmail = { id: 456 };
      const pessoa: IPessoaFisica = { id: 31286 };
      email.pessoa = pessoa;

      const pessoaFisicaCollection: IPessoaFisica[] = [{ id: 1723 }];
      jest.spyOn(pessoaFisicaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoaFisicaCollection })));
      const additionalPessoaFisicas = [pessoa];
      const expectedCollection: IPessoaFisica[] = [...additionalPessoaFisicas, ...pessoaFisicaCollection];
      jest.spyOn(pessoaFisicaService, 'addPessoaFisicaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ email });
      comp.ngOnInit();

      expect(pessoaFisicaService.query).toHaveBeenCalled();
      expect(pessoaFisicaService.addPessoaFisicaToCollectionIfMissing).toHaveBeenCalledWith(
        pessoaFisicaCollection,
        ...additionalPessoaFisicas.map(expect.objectContaining),
      );
      expect(comp.pessoaFisicasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const email: IEmail = { id: 456 };
      const pessoa: IPessoaFisica = { id: 2492 };
      email.pessoa = pessoa;

      activatedRoute.data = of({ email });
      comp.ngOnInit();

      expect(comp.pessoaFisicasSharedCollection).toContain(pessoa);
      expect(comp.email).toEqual(email);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmail>>();
      const email = { id: 123 };
      jest.spyOn(emailFormService, 'getEmail').mockReturnValue(email);
      jest.spyOn(emailService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ email });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: email }));
      saveSubject.complete();

      // THEN
      expect(emailFormService.getEmail).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(emailService.update).toHaveBeenCalledWith(expect.objectContaining(email));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmail>>();
      const email = { id: 123 };
      jest.spyOn(emailFormService, 'getEmail').mockReturnValue({ id: null });
      jest.spyOn(emailService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ email: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: email }));
      saveSubject.complete();

      // THEN
      expect(emailFormService.getEmail).toHaveBeenCalled();
      expect(emailService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmail>>();
      const email = { id: 123 };
      jest.spyOn(emailService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ email });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(emailService.update).toHaveBeenCalled();
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
