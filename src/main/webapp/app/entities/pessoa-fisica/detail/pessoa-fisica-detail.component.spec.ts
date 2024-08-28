import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PessoaFisicaDetailComponent } from './pessoa-fisica-detail.component';

describe('PessoaFisica Management Detail Component', () => {
  let comp: PessoaFisicaDetailComponent;
  let fixture: ComponentFixture<PessoaFisicaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PessoaFisicaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PessoaFisicaDetailComponent,
              resolve: { pessoaFisica: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PessoaFisicaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PessoaFisicaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pessoaFisica on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PessoaFisicaDetailComponent);

      // THEN
      expect(instance.pessoaFisica()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
