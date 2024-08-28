import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PessoajuridicaDetailComponent } from './pessoajuridica-detail.component';

describe('Pessoajuridica Management Detail Component', () => {
  let comp: PessoajuridicaDetailComponent;
  let fixture: ComponentFixture<PessoajuridicaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PessoajuridicaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PessoajuridicaDetailComponent,
              resolve: { pessoajuridica: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PessoajuridicaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PessoajuridicaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pessoajuridica on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PessoajuridicaDetailComponent);

      // THEN
      expect(instance.pessoajuridica()).toEqual(expect.objectContaining({ id: 123 }));
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
